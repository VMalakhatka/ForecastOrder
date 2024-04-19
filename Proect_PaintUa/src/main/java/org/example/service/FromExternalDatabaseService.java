package org.example.service;

import org.example.dto.dataFromDb.in.*;
import org.example.dto.dataFromDb.out.GeMoveByGoodsListAndStockListAndDataStartEndDtoOut;
import org.example.dto.dataFromDb.out.GetDataByGoodsListAndStockListDtoOut;
import org.example.entity.data.from.db.Assemble;
import org.example.entity.data.from.db.Goods;
import org.example.entity.data.from.db.Rest;
import org.example.entity.data.from.db.StockParam;
import org.example.entity.forecast.ForecastTemplate;
import org.example.entity.forecast.SetStockTT;
import org.example.exception.DataNotValidException;
import org.example.exception.NotEnoughDataException;
import org.example.exception.RabbitNotAnswerException;
import org.example.mapper.data.from.db.in.GoodsInMapper;
import org.example.mapper.data.from.db.in.MoveInMapper;
import org.example.mapper.data.from.db.in.RestMapper;
import org.example.mapper.data.from.db.in.StockParamMapper;
import org.example.rabbitMQ.consumer.*;
import org.example.repository.data.from.db.AssembleSequenceRepository;
import org.example.repository.data.from.db.GoodsRepository;
import org.example.repository.forecast.ForecastTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FromExternalDatabaseService {

    GoodsInDao goodsInDao;
    GoodsInMapper goodsInMapper;
    MoveInMapper moveInMapper;
    RestMapper restMapper;
    StockParamMapper stockParamMapper;
    GoodsRepository goodsRepository;
    ForecastTemplateRepository forecastTemplateRepository;
    AssembleSequenceRepository assembleSequenceRepository;
    MoveInDao moveInDao;
    RestDao restDao;
    StockParamDao stockParamDao;
    AssembleDao assembleDao;


    @Autowired
    public FromExternalDatabaseService(GoodsInDao goodsInDao, GoodsInMapper goodsInMapper, MoveInMapper moveInMapper, RestMapper restMapper, StockParamMapper stockParamMapper, GoodsRepository goodsRepository, ForecastTemplateRepository forecastTemplateRepository, AssembleSequenceRepository assembleSequenceRepository, MoveInDao moveInDao, RestDao restDao, StockParamDao stockParamDao, AssembleDao assembleDao) {
        this.goodsInDao = goodsInDao;
        this.goodsInMapper = goodsInMapper;
        this.moveInMapper = moveInMapper;
        this.restMapper = restMapper;
        this.stockParamMapper = stockParamMapper;
        this.goodsRepository = goodsRepository;
        this.forecastTemplateRepository = forecastTemplateRepository;
        this.assembleSequenceRepository = assembleSequenceRepository;
        this.moveInDao = moveInDao;
        this.restDao = restDao;
        this.stockParamDao = stockParamDao;
        this.assembleDao = assembleDao;
    }

    @Transactional
    public ForecastTemplate saveListOfGoods(ForecastTemplate forecastTemplate) throws NotEnoughDataException, DataNotValidException, RabbitNotAnswerException, ConnectException {
        if (!forecastTemplate.getGoodsSet().isEmpty())
            throw new DataNotValidException("The product set in this forecast is not empty You cannot load new data into this forecast. ");
        List<GoodsDtoIn> goodsDtoInList = goodsInDao.getGoodsBySupplierAndStockId(forecastTemplate.getType().name(), forecastTemplate.getSupplier(), forecastTemplate.getIdMainStock());
        if (goodsDtoInList == null) throw new NotEnoughDataException("Not goods for forecast");
        goodsDtoInList.forEach(dto -> forecastTemplate.addGoods(goodsInMapper.toGoodsEntity(dto)));
        forecastTemplateRepository.save(forecastTemplate);
        return forecastTemplate;
    }

    @Transactional
    public ForecastTemplate saveListOfChildForForecast(ForecastTemplate forecastTemplate) throws NotEnoughDataException, DataNotValidException {
        if (forecastTemplate.getGoodsSet().isEmpty())
            throw new NotEnoughDataException("Not goods to compile for requesting child from external database ");
        List<AssembleDtoIn> assembleDtoIns = assembleDao.getAssembleByGoodsList(
                new GetDataByGoodsListAndStockListDtoOut(getCodeGoods(forecastTemplate.getGoodsSet()), null), null);
        Queue<Long> idQueue = new LinkedList<>(assembleSequenceRepository.getNextSequenceValues(assembleDtoIns.size()));
        TreeMap<String, List<Assemble>> assParentTreeMap = new TreeMap<>();
        TreeMap<String, Assemble> assChildTreeMap = new TreeMap<>();
        for (AssembleDtoIn a : assembleDtoIns) {
            if (assChildTreeMap.containsKey(a.childCode()))
                throw new DataNotValidException("A product can only be inherited once, otherwise you don't know which parent it belongs to.");
            Assemble assemble = new Assemble(idQueue.poll(), a.quntity(), null, null);
            assChildTreeMap.put(a.childCode(), assemble);
            if (assParentTreeMap.containsKey(a.parentCode())) assParentTreeMap.get(a.parentCode()).add(assemble);
            else assParentTreeMap.put(a.parentCode(), new ArrayList<>(List.of(assemble)));
        }
        for (Goods goods : forecastTemplate.getGoodsSet()) {
            if (assParentTreeMap.containsKey(goods.getCodArtic()))
                assParentTreeMap.get(goods.getCodArtic()).forEach(goods::addAssembleParentSet);
            if (assChildTreeMap.containsKey(goods.getCodArtic())) {
                Assemble assembleChild = assChildTreeMap.get(goods.getCodArtic());
                assembleChild.setChildGood(goods);
                goods.setAssembleChild(assembleChild);
            }
        }

        for (Map.Entry<String, Assemble> a : assChildTreeMap.entrySet())
            if (a.getValue().getChildGood() == null && a.getValue().getParentGood() != null)
                a.getValue().getParentGood().remoteAssembleParentSet(a.getValue()); //TODO надо бы подтянуть недостающих детей но пока удаляем эту ветку
        //TODO возможна ситуация наоборот нет родителя - сделать позже
        return forecastTemplate;
    }

    @Transactional
    public ForecastTemplate saveListOfMoveForForecast(ForecastTemplate forecastTemplate) throws NotEnoughDataException {

        if (forecastTemplate.getGoodsSet().isEmpty())
            throw new NotEnoughDataException("Not goods to compile for requesting move from external database ");
        List<Long> idStockList = listStockOnlyWithTip(forecastTemplate.getSetStockTTSet());
        if (idStockList == null || idStockList.isEmpty())
            throw new NotEnoughDataException("Not specified stock to compile for requesting move from external database ");
        List<String> codeArticList = forecastTemplate.getGoodsSet().stream().map(Goods::getCodArtic).toList();
        String start = forecastTemplate.getStartAnalysis().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String end = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        GeMoveByGoodsListAndStockListAndDataStartEndDtoOut getMove = new GeMoveByGoodsListAndStockListAndDataStartEndDtoOut(codeArticList, idStockList, start, end);

        PriorityQueue<MoveDtoIn> prQGoodsMoveIn = new PriorityQueue<>((move1, move2) -> move2.NamePredm().compareTo(move1.NamePredm()));
        List<MoveDtoIn> moveDtoInList = moveInDao.getMoveByGoodsListAndStockList(getMove);
        if (moveDtoInList == null) return forecastTemplate;
        prQGoodsMoveIn.addAll(moveDtoInList);

        String firstNamePredm;
        MoveDtoIn dto = prQGoodsMoveIn.poll();
        while (!prQGoodsMoveIn.isEmpty() || dto != null) {
            firstNamePredm = Objects.requireNonNull(dto).NamePredm();
            String finalFirstNamePredm = firstNamePredm;
            Goods goods = forecastTemplate.getGoodsSet().stream().filter(g -> g.getCodArtic().equals(finalFirstNamePredm)).findFirst().orElse(null);
            if (goods!=null)
                while (true) {
                    goods.addGoodsMove(moveInMapper.toGoodsMove(dto));
                    if (!prQGoodsMoveIn.isEmpty())
                        dto = prQGoodsMoveIn.poll();
                    else {
                        dto = null;
                        break;
                    }
                    if (!firstNamePredm.equals(dto.NamePredm())) break;
                }
        }
        return forecastTemplate;
    }

    @Transactional
    public ForecastTemplate saveListOfRestForForecast(ForecastTemplate forecastTemplate) throws NotEnoughDataException {
        if (forecastTemplate.getGoodsSet().isEmpty())
            throw new NotEnoughDataException("Not goods to compile for requesting rest from external database ");
        List<Long> idStockList = forecastTemplate.getSetStockTTSet().stream().map(SetStockTT::getIdStock).toList();
        if (idStockList == null || idStockList.isEmpty())
            throw new NotEnoughDataException("Not specified stock to compile for requesting rest from external database ");
        List<String> codeArticList = forecastTemplate.getGoodsSet().stream().map(Goods::getCodArtic).toList();
        List<RestDtoIn> restDtoInList = restDao.getRestByGoodsAndStockList(new GetDataByGoodsListAndStockListDtoOut(codeArticList, idStockList));
        if (restDtoInList == null) return forecastTemplate;
        TreeMap<String, List<Rest>> restMap = new TreeMap<>();
        restDtoInList.forEach(r -> {
            if (restMap.containsKey(r.codArtc()))
                restMap.get(r.codArtc()).add(restMapper.toRest(r));
            else
                restMap.put(r.codArtc(), new ArrayList<>(List.of(restMapper.toRest(r))));
        });
        for (Goods g : forecastTemplate.getGoodsSet()) {
            List<Rest> restList=restMap.get(g.getCodArtic());
            if(restList!=null && !restList.isEmpty())
                for (Rest r: restList) g.addRest(r);
        }
        return forecastTemplate;
    }

    @Transactional
    public ForecastTemplate saveStockParam(ForecastTemplate forecastTemplate) throws NotEnoughDataException {
        if (forecastTemplate.getGoodsSet().isEmpty())
            throw new NotEnoughDataException("Not goods to compile for requesting stockParam from external database ");
        List<Long> idStockList = listStockOnlyWithTip(forecastTemplate.getSetStockTTSet());
        if (idStockList == null || idStockList.isEmpty())
            throw new NotEnoughDataException("Not specified stock to compile for requesting stockParam from external database ");
        List<StockParamDtoIn> stockParamDtoIns = stockParamDao.getStockParamByGoodsAndStockList(
                new GetDataByGoodsListAndStockListDtoOut(getCodeGoods(forecastTemplate.getGoodsSet()), idStockList));
        if (stockParamDtoIns == null) return forecastTemplate;
        TreeMap<String, List<StockParam>> paramMap = new TreeMap<>();
        stockParamDtoIns.forEach(p -> {
            if (paramMap.containsKey(p.codArtc()))
                paramMap.get(p.codArtc()).add(stockParamMapper.toStockParam(p));
            else
                paramMap.put(p.codArtc(), new ArrayList<>(List.of(stockParamMapper.toStockParam(p))));
        });
        for (Goods g : forecastTemplate.getGoodsSet()) {
            List<StockParam> stockParamList=paramMap.get(g.getCodArtic());
            if(stockParamList!=null && !stockParamList.isEmpty())
                for (StockParam s: stockParamList) g.addStockParam(s);
        }
        return forecastTemplate;
    }


    private List<String> getCodeGoods(Set<Goods> goodsSet) {
        return goodsSet.stream().map(Goods::getCodArtic).toList();
    }

    private List<Long> listStockOnlyWithTip(Set<SetStockTT> setST) {
        return setST.stream().filter(set -> !set.getStockTipSaleSet().isEmpty()).map(SetStockTT::getIdStock)
                .toList();
    }
}
