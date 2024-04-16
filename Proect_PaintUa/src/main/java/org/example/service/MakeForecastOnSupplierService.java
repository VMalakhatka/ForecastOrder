package org.example.service;


import org.example.entity.dataFromDb.Assemble;
import org.example.entity.dataFromDb.Goods;
import org.example.entity.dataFromDb.GoodsMove;
import org.example.entity.dataFromDb.StockParam;
import org.example.entity.forecast.ForecastTemplate;
import org.example.entity.forecast.SetStockTT;
import org.example.entity.forecast.StockTipSale;
import org.example.entity.entityEnum.TypeOfForecast;
import org.example.entity.templates.SetStockTtTemplate;
import org.example.entity.templates.StockTipSaleTemplate;
import org.example.entity.templates.Template;
import org.example.exception.DataNotValidException;
import org.example.exception.NotEnoughDataException;
import org.example.exception.NotFindByIDException;
import org.example.exception.RabbitNotAnswerException;
import org.example.repository.forecast.ForecastTemplateRepository;
import org.example.repository.templates.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Сервис принимает на вход номер шаблона заказа, поставщика(по которому заказ формировать)
 * Дату начала анализа и конец анализа - если даты не заданы, то отсчитывается дата окончания
 * анализа от ткущей, Текущая дата - дата начала анализа
 */
@Service
public class MakeForecastOnSupplierService {
    TemplateRepository templateRepository;
    ForecastTemplateRepository forecastTemplateRepository;
    FromExternalDatabaseService fromExternalDatabaseService;


    @Autowired
    public MakeForecastOnSupplierService(TemplateRepository templateRepository, ForecastTemplateRepository forecastTemplateRepository, FromExternalDatabaseService fromExternalDatabaseService) {
        this.templateRepository = templateRepository;
        this.forecastTemplateRepository = forecastTemplateRepository;
        this.fromExternalDatabaseService = fromExternalDatabaseService;
    }


    public List<ForecastTemplate> getAllForecast() {
        return forecastTemplateRepository.findAll();
    }

    public void delAllForecasts() {
        forecastTemplateRepository.deleteAll();
    }

    @Transactional
    public void delForecastTemplate(long id) throws NotFindByIDException {
        ForecastTemplate fT = forecastTemplateRepository.findById(id).orElseThrow(() ->
                new NotFindByIDException("Could not find the forecast by ID in the delForecastTemplate method"));
        forecastTemplateRepository.delete(fT);
    }

    public ForecastTemplate getForecastById(long id) throws NotFindByIDException {
        return forecastTemplateRepository.findById(id).orElseThrow(() ->
                new NotFindByIDException("forecast not found by ID"));
    }

    public Set<Goods> getGoodsListByForecastId(long id) throws NotFindByIDException {
        return forecastTemplateRepository.findById(id).orElseThrow(() ->
                new NotFindByIDException("forecast not found by ID when you searched for the product ")).getGoodsSet();
    }


    public ForecastTemplate run(Long id_template, String supplier) throws DataNotValidException, NotEnoughDataException, ConnectException, RabbitNotAnswerException {
        Optional<Template> templateOptional = templateRepository.findById(id_template);
        if (templateOptional.isEmpty())
            throw new DataNotValidException("There is no such template");
        ForecastTemplate forecastTemplate =
                saveTemplateToForecast(templateOptional.orElse(null), supplier);
        ForecastTemplate forecastTemplateSaved = forecastTemplateRepository.save(forecastTemplate);

        fromExternalDatabaseService.saveListOfGoods(forecastTemplateSaved);

        fromExternalDatabaseService.saveListOfMoveForForecast(forecastTemplateSaved);


        fromExternalDatabaseService.saveListOfRestForForecast(forecastTemplateSaved);

        fromExternalDatabaseService.saveStockParam(forecastTemplateSaved);

        fromExternalDatabaseService.saveListOfChildForForecast(forecastTemplateSaved);

        sale(forecastTemplateSaved);
        rest(forecastTemplateSaved);
        dayWhenGoodsNotOnStock(forecastTemplateSaved);
        countNotSaleAndOrderAndMin(forecastTemplateSaved);
        plusKinder(forecastTemplateSaved);
        orderForTT(forecastTemplateSaved);

        forecastTemplateSaved = forecastTemplateRepository.save(forecastTemplate);

        return forecastTemplateSaved;
    }

    private void orderForTT(ForecastTemplate forecastTemplate) {
        Set<Goods> gS=forecastTemplate.getGoodsSet();
        for(Goods g:gS){
            StockParam stockParam=g.getStockParams().stream().filter(sp->sp.getIdStock()==forecastTemplate.getIdMainStock()).findFirst().orElse(null);
            if(stockParam!=null && stockParam.getMinTvrZap()>0 && stockParam.getTipOrder()>10 && g.getForecast().getOrderWithoutPack()>0) { // TipOrder -- 11 и больше - заказ у поставщика
                double order=Math.floor(g.getForecast().getOrderWithoutPack()+0.7); //округляем от 0.3 в большую сторону
                if((order + g.getForecast().getRestTT())>=stockParam.getMaxTvrZap())
                    order=stockParam.getMaxTvrZap()-g.getForecast().getRestTT();
                order=Math.floor(order/g.getEdnVUpak())*g.getEdnVUpak();
                g.getForecast().setOrderTT(order);
            }
        }
    }

    private double getKindQuantity(Goods good) {
        double sum = 0;
        if (!good.getAssemblePerentSet().isEmpty())
            for (Assemble assemble : good.getAssemblePerentSet())
                sum += getKindQuantity(assemble.getChildGood());
        sum += good.getForecast().getOrderWithoutPack();
        good.getForecast().setOrderWithoutPack(sum);

        if (good.getAssembleChild() != null) {
            sum = sum / good.getAssembleChild().getQuantity();
            good.getForecast().setOrderWithoutPack(0.0);
        }
        return sum;
    }

    private void plusKinder(ForecastTemplate forecastTemplate) {
        forecastTemplate.getGoodsSet().forEach(good -> {
            if (good.getAssembl() == 1)
                good.getForecast().setOrderWithoutPack(good.getForecast().getOrderWithoutPack() + getKindQuantity(good));
        });
    }


    private void countNotSaleAndOrderAndMin(ForecastTemplate forecastTemplate) {
        Set<Long> stockWithMin = forecastTemplate.getSetStockTTSet().stream().filter(SetStockTT::isMin).map(SetStockTT::getIdStock).collect(Collectors.toSet());
        forecastTemplate.getGoodsSet().forEach(goods -> {

                    double minPlus = 0;
                    for (long st : stockWithMin)
                        for (StockParam par : goods.getStockParams())
                            if (st == par.getIdStock())
                                minPlus += par.getMinTvrZap();

                    if (forecastTemplate.getOrderForDay() != goods.getForecast().getNotOnStock()) {
                        goods.getForecast().setNotSaleAndSale(
                                goods.getForecast().getSale() / (forecastTemplate.getOrderForDay() - goods.getForecast().getNotOnStock())
                                        * forecastTemplate.getOrderForDay());
                    }

                    double saleWithKoef = goods.getForecast().getSale() * forecastTemplate.getKoefToRealSale();
                    double forecastWithMin = goods.getForecast().getNotSaleAndSale() + minPlus;
                    double orderWithForecast;
                    if (saleWithKoef <= minPlus) orderWithForecast = minPlus;
                    else orderWithForecast = Math.min(saleWithKoef, forecastWithMin);

                    goods.getForecast().setOrderWithoutPack(goods.getForecast().getRestTT() >= orderWithForecast ? 0 :
                            orderWithForecast - goods.getForecast().getRestTT());
                }
        );
    }

    private ForecastTemplate saveTemplateToForecast(Template template, String supplier) throws NotEnoughDataException {
        ForecastTemplate forecastTemplate = new ForecastTemplate();
        forecastTemplate.setOrderForDay(template.getOrderForDay());
        if (template.getStartAnalysis() == null || template.getEndAnalysis() == null) {
            forecastTemplate.setEndAnalysis(LocalDateTime.now());
            forecastTemplate.setStartAnalysis(
                    forecastTemplate.getEndAnalysis().minusDays(template.getOrderForDay()));
        } else {
            forecastTemplate.setStartAnalysis(template.getStartAnalysis());
            forecastTemplate.setEndAnalysis(template.getEndAnalysis());
            forecastTemplate.setOrderForDay((int) Math.abs(Duration.between(forecastTemplate.getEndAnalysis(),
                    forecastTemplate.getStartAnalysis()).toDays()));
        }
        forecastTemplate.setName(template.getName());
        forecastTemplate.setType(TypeOfForecast.valueOf(template.getType()));
        forecastTemplate.setKoefToRealSale(template.getKoefToRealSale());
        forecastTemplate.setSupplier(supplier);
        forecastTemplate.setIdMainStock(template.getIdMainStock());
        if (template.getSetStockTtTemplates().isEmpty())
            throw new NotEnoughDataException("Not specified stock for template-" + template.getId());
        template.getSetStockTtTemplates().forEach(set ->
                forecastTemplate.addSetStockTT(setStockTTtoSetStockTtTemplate(set)));
        return forecastTemplate;
    }

    private SetStockTT setStockTTtoSetStockTtTemplate(SetStockTtTemplate setTemplate) {
        SetStockTT set = new SetStockTT();
        set.setIdTT(setTemplate.getIdTT());
        set.setNameTT(setTemplate.getNameTT());
        set.setIdStock(setTemplate.getIdStock());
        set.setNameStock(setTemplate.getNameStock());
        set.setRole(setTemplate.getRole());
        set.setMin(setTemplate.isMin());
        set.setMax(setTemplate.isMax());
        set.setFasovka(setTemplate.isFasovka());
        setTemplate.getStockTipSaleTemplateHashSet().forEach(setTip ->
                set.addStockTipSale(setTipTemplatesToSetTipForecast(setTip)));
        return set;
    }

    private StockTipSale setTipTemplatesToSetTipForecast(StockTipSaleTemplate setTip) {
        StockTipSale tipSale = new StockTipSale();
        tipSale.setOrgPredm(setTip.getOrgPredm());
        tipSale.setTypdocmPr(setTip.getTypdocmPr());
        tipSale.setVidDoc(setTip.getVidDoc());
        tipSale.setEqual(setTip.isEqual());
        return tipSale;
    }


    private void sale(ForecastTemplate forecastTemplate) {
        forecastTemplate.getSetStockTTSet().forEach(setStockTT -> {
            if (!setStockTT.getStockTipSaleSet().isEmpty())
                saleStock(setStockTT, forecastTemplate);
        });

    }

    private void rest(ForecastTemplate forecastTemplate) {
        Set<Long> stockSet = forecastTemplate.getSetStockTTSet().stream().map(SetStockTT::getIdStock).collect(Collectors.toSet());
        forecastTemplate.getGoodsSet().forEach(good -> good.getRestSet().forEach(rest ->
        {
            if (rest.getKonKolich() != 0 && stockSet.contains(rest.getIdStock()))
                good.getForecast().setRestTT(good.getForecast().getRestTT() + rest.getKonKolich());
        }));
    }

    private void dayWhenGoodsNotOnStock(ForecastTemplate forecastTemplate) {
        Set<Long> stockSet = forecastTemplate.getSetStockTTSet().stream().
                filter(setStockTT -> !setStockTT.getStockTipSaleSet().isEmpty()). // возможны еще какие-либо роли для подсчета не продано
                //setStockTT.getRole().equals(StockRole.PT) || setStockTT.getRole().equals(StockRole.OPT)
                        map(SetStockTT::getIdStock).collect(Collectors.toSet());
        forecastTemplate.getGoodsSet().forEach(goods -> {
            if (!goods.getGoodsMoveSet().isEmpty()) {
                stockSet.forEach(stock -> {//TODO all stock calculate rest

                    PriorityQueue<GoodsMove> moves = moveSetToPriorityQueueByData(goods.getGoodsMoveSet(), stock);

                    if (!moves.isEmpty()) {
                        long nullDay = 0;
                        double rest = goods.getRestSet().stream().filter(r -> r.getIdStock() == stock).findFirst().orElseThrow().getKonKolich();
                        LocalDateTime setNul=forecastTemplate.getEndAnalysis();
                        double newRest;
                        while (!moves.isEmpty()) {
                            GoodsMove m = moves.poll();
                            m.setRest(rest);
                            newRest = rest + switch (m.getTypDocmPr()) {
                                case P -> -m.getQuantity();
                                case R -> m.getQuantity();
                                case S -> 0.0;
                            };
                     //       if (m.getData().isAfter(forecastTemplate.getStartAnalysis().minusDays(1)) && m.getData().isBefore(forecastTemplate.getEndAnalysis().plusDays(1))) {
                            if (forecastTemplate.inForecastDate(m.getData())) {
                                if (rest <= 0 && newRest > 0.0)
                                    nullDay += Math.abs(Duration.between(m.getData(), setNul).toDays());
                                if (newRest <= 0 && rest > 0) setNul = m.getData();
//                            } else if (m.getData().isBefore(forecastTemplate.getStartAnalysis()) && setNul.isAfter(forecastTemplate.getStartAnalysis().minusDays(1)) && setNul.isBefore(forecastTemplate.getEndAnalysis().plusDays(1))
                            } else if (m.getData().isBefore(forecastTemplate.getStartAnalysis()) && forecastTemplate.inForecastDate(setNul)
                                    && newRest>0.0 && rest<=0) {
                                nullDay += Math.abs(Duration.between(forecastTemplate.getStartAnalysis(), setNul).toDays());
                            }
                            rest = newRest;
                        }
                        if (rest <= 0 && setNul.isAfter(forecastTemplate.getStartAnalysis().minusDays(1))) // TODO but sum only stock with tips
                            nullDay += Math.abs(Duration.between(forecastTemplate.getStartAnalysis(), setNul).toDays());
                        goods.getForecast().setNotOnStock(goods.getForecast().getNotOnStock() + nullDay);
                    }
                });
            } else if (goods.getForecast().getRestTT() <= 0)
                goods.getForecast().setNotOnStock(Math.abs(Duration.between(forecastTemplate.getEndAnalysis(),
                        forecastTemplate.getStartAnalysis()).toDays()));
        });
    }

    private PriorityQueue<GoodsMove> moveSetToPriorityQueueByData(Set<GoodsMove> goodsMoveSet, Long stock) {
        PriorityQueue<GoodsMove> prQGoodsM = new PriorityQueue<>((move1, move2) ->
                move2.getData().compareTo(move1.getData()));
        goodsMoveSet.stream().filter(m -> m.getIdStock() == stock).forEach(prQGoodsM::add);
        return prQGoodsM;
    }

    private void saleStock(SetStockTT setStockTT, ForecastTemplate forecastTemplate) {
        long stock = setStockTT.getIdStock();
        Set<StockTipSale> tips = setStockTT.getStockTipSaleSet();
        if (Objects.requireNonNull(tips.stream().findFirst().orElse(null)).isEqual()) {
            forecastTemplate.getGoodsSet().forEach(goods -> goods.getGoodsMoveSet().forEach(move -> {
//                if (move.getData().isEqual(forecastTemplate.getStartAnalysis()) ||
//                        move.getData().isEqual(forecastTemplate.getEndAnalysis()) ||
//                        (move.getData().isAfter(forecastTemplate.getStartAnalysis()) &&
//                                move.getData().isBefore(forecastTemplate.getEndAnalysis())) &&
                if (forecastTemplate.inForecastDate(move.getData()) &&
                                stock == move.getIdStock()) {
                    tips.forEach(t -> {
                        boolean yes = false;
                        if (t.getTypdocmPr().equals(move.getTypDocmPr())) {
                            if (t.getOrgPredm().equals("ALL")) {
                                yes = true;
                            } else if (t.getOrgPredm().equals(move.getOrgPredm())) yes = true;
                            if (yes) {
                                yes = t.getVidDoc().equals(move.getVidDoc());
                                if (t.getVidDoc().equals("ALL")) yes = true;
                            }
                        }
                        if (yes)
                            goods.getForecast().setSale(goods.getForecast().getSale() + move.getQuantity());
                    });
                }
            }));
        } else {
            forecastTemplate.getGoodsSet().forEach(goods -> goods.getGoodsMoveSet().forEach(move -> {
//                if (move.getData().isEqual(forecastTemplate.getStartAnalysis()) ||
//                        move.getData().isEqual(forecastTemplate.getEndAnalysis()) ||
//                        (move.getData().isAfter(forecastTemplate.getStartAnalysis()) &&
//                                move.getData().isBefore(forecastTemplate.getEndAnalysis())) &&
                if (forecastTemplate.inForecastDate(move.getData()) &&
                                stock == move.getIdStock()) {
                    AtomicBoolean no = new AtomicBoolean(false);
                    AtomicBoolean yes = new AtomicBoolean(false);
                    tips.forEach(t -> {
                        if (!no.get()) {
                            if (t.getTypdocmPr().equals(move.getTypDocmPr())) {
                                yes.set(true);
                                if (t.getOrgPredm().equals("ALL")) {
                                    no.set(true);
                                } else if (t.getOrgPredm().equals(move.getOrgPredm())) no.set(true);
                                if (no.get()) {
                                    no.set(t.getVidDoc().equals(move.getVidDoc()));
                                    if (t.getVidDoc().equals("ALL")) no.set(true);
                                }
                            }
                        }
                    });
                    if (yes.get() && !no.get())
                        goods.getForecast().setSale(goods.getForecast().getSale() + move.getQuantity());
                }
            }));
        }
    }

}
