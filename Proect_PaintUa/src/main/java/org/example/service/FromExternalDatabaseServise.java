package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.dao.*;
import org.example.dto.data_from_db.in.*;
import org.example.dto.data_from_db.out.GeMoveByGoodsListAndStockListAndDataStartEndDtoOut;
import org.example.dto.data_from_db.out.GetDataByGoodsListAndStockListDtoOut;
import org.example.dto.enum_dto.TypeOfRequestRabbit;
import org.example.entity.data_from_db.Assemble;
import org.example.entity.data_from_db.Goods;
import org.example.entity.data_from_db.Rest;
import org.example.entity.data_from_db.StockParam;
import org.example.entity.forecast.ForecastTemplate;
import org.example.entity.forecast.SetStockTT;
import org.example.exeption.DataNotValid;
import org.example.exeption.NotEnoughData;
import org.example.mapper.data_from_db.in.GoodsInMapper;
import org.example.mapper.data_from_db.in.MoveInMapper;
import org.example.mapper.data_from_db.in.RestMapper;
import org.example.mapper.data_from_db.in.StockParamMapper;
import org.example.repository.data_from_db.AssembleSequenceRepository;
import org.example.repository.data_from_db.GoodsRepository;
import org.example.repository.forecast.ForecastTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class FromExternalDatabaseServise {

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

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public FromExternalDatabaseServise(GoodsInDao goodsInDao, GoodsInMapper goodsInMapper, MoveInMapper moveInMapper, RestMapper restMapper, StockParamMapper stockParamMapper, GoodsRepository goodsRepository, ForecastTemplateRepository forecastTemplateRepository, AssembleSequenceRepository assembleSequenceRepository, MoveInDao moveInDao, RestDao restDao, StockParamDao stockParamDao, AssembleDao assembleDao, EntityManager entityManager) {
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
        this.entityManager = entityManager;
    }

    @Transactional
    public ForecastTemplate saveListOfGoodsBySupplier(ForecastTemplate forecastTemplate) throws NotEnoughData, DataNotValid {
        if(!forecastTemplate.getGoodsSet().isEmpty()) throw new DataNotValid("The product set in this forecast is not empty You cannot load new data into this forecast. ");
        SetStockTT setStockTT=forecastTemplate.getSetStockTTSet().stream().
                filter(set -> set.getStockTipSaleSet().isEmpty()).findFirst().orElseThrow(()->
                        new NotEnoughData("Not specified stock to compile for requesting goods from external database "));
       // List<GoodsDtoIn> goodsDtoInList = goodsInDao.getGoodsBySupplierAndStockId(forecastTemplate.getSupplier(),setStockTT.getIdStock());
        List<GoodsDtoIn> goodsDtoInList = goodsInDao.getGoodsBySupplierAndStockId(forecastTemplate.getSupplier(), forecastTemplate.getIdMainStock());
        if (goodsDtoInList == null) throw new NotEnoughData("Not goods for forecast");
        goodsDtoInList.forEach(dto->forecastTemplate.addGoods(goodsInMapper.toGoodsEntity(dto)));
        forecastTemplateRepository.save(forecastTemplate);
        //TODO flash?
        return forecastTemplate;
    }
    @Transactional
    public ForecastTemplate saveListOfChildForForecast(ForecastTemplate forecastTemplate) throws NotEnoughData, DataNotValid {
        if(forecastTemplate.getGoodsSet().isEmpty()) throw new NotEnoughData("Not goods to compile for requesting child from external database ");
        List<AssembleDtoIn> assembleDtoIns=assembleDao.getAssembleByGoodsList(
                new GetDataByGoodsListAndStockListDtoOut(getCodeGoods(forecastTemplate.getGoodsSet()),null));
        Queue<Long> idQueue = new LinkedList<>(assembleSequenceRepository.getNextSequenceValues(assembleDtoIns.size()));
        TreeMap<String, List<Assemble>> assParentTreeMap=new TreeMap<>();
        TreeMap<String, Assemble> assChildTreeMap=new TreeMap<>();
        for (AssembleDtoIn a : assembleDtoIns){
            if (assChildTreeMap.containsKey(a.childCode()))
                throw new DataNotValid("A product can only be inherited once, otherwise you don't know which parent it belongs to.");
            Assemble assemble =new Assemble(idQueue.poll(),a.quntity(),null,null);
            assChildTreeMap.put(a.childCode(),assemble);
            if(assParentTreeMap.containsKey(a.parentCode())) assParentTreeMap.get(a.parentCode()).add(assemble);
            else assParentTreeMap.put(a.parentCode(),new ArrayList<>(List.of(assemble)));
        }
        for(Goods goods: forecastTemplate.getGoodsSet()){
            if(assParentTreeMap.containsKey(goods.getCodArtic()))
                assParentTreeMap.get(goods.getCodArtic()).forEach(goods::addAssembleParentSet);
            if (assChildTreeMap.containsKey(goods.getCodArtic())) {
                Assemble assembleChild=assChildTreeMap.get(goods.getCodArtic());
                assembleChild.setChildGood(goods);
                goods.setAssembleChild(assembleChild);
            }
        }

        for (Map.Entry<String, Assemble> a: assChildTreeMap.entrySet())
            if (a.getValue().getChildGood() == null && a.getValue().getParentGood()!=null)
                    a.getValue().getParentGood().remoteAssembleParentSet(a.getValue()); //TODO надо бы подтянуть недостющих детей но пока зануляем эту ветку
        //TODO возможна ситуация наоборот нет родителя - сделать позже

        forecastTemplate.getGoodsSet().forEach(g->{
            if (g.getAssembleChild()!=null) {
                System.out.println("ребенок - "+g.getAssembleChild().getChildGood().getCodArtic()+" родитель - "+ g.getAssembleChild().getParentGood().getCodArtic());
                System.out.println("________________________");
            }
            if (!g.getAssemblePerentSet().isEmpty()){
                System.out.println("Родитель --______________");
                g.getAssemblePerentSet().forEach(a->{
                    System.out.println("             родитель- "+a.getId()+" - "+ a.getParentGood().getCodArtic());
                    System.out.println("             ребенок++ "+a.getId()+" - "+ a.getChildGood().getCodArtic());
                    System.out.println("++++++++++++++++++++");
                });
            }
        });

        //TODO flash?
        return forecastTemplate;
   }

@Transactional
    public ForecastTemplate saveListOfMoveForForecast(ForecastTemplate forecastTemplate) throws NotEnoughData {

        if(forecastTemplate.getGoodsSet().isEmpty()) throw new NotEnoughData("Not goods to compile for requesting move from external database ");
        List<Long> idStockList=listStockOnlyWithTip(forecastTemplate.getSetStockTTSet());

           //     forecastTemplate.getSetStockTTSet().stream().filter(set -> !set.getStockTipSaleSet().isEmpty()).map(SetStockTT::getIdStock).toList();
        if (idStockList == null) throw new NotEnoughData("Not specified stock to compile for requesting move from external database ");
        List<String> codeArticList=forecastTemplate.getGoodsSet().stream().map(Goods::getCodArtic).toList();
        String start=forecastTemplate.getStartAnalysis().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String end=forecastTemplate.getEndAnalysis().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        GeMoveByGoodsListAndStockListAndDataStartEndDtoOut getMove=new GeMoveByGoodsListAndStockListAndDataStartEndDtoOut(codeArticList,idStockList,start,end);

        PriorityQueue<MoveDtoIn> prQGoodsMoveIn=new PriorityQueue<>((move1, move2) -> move2.NamePredm().compareTo(move1.NamePredm()));
        List<MoveDtoIn> moveDtoInList=moveInDao.getMoveByGoodsListAndStockList(getMove);
        if (moveDtoInList == null) return forecastTemplate;
        prQGoodsMoveIn.addAll(moveDtoInList);

        String firstNamePredm;
        MoveDtoIn dto=prQGoodsMoveIn.poll();
        while (!prQGoodsMoveIn.isEmpty() || dto!=null){
                firstNamePredm=dto.NamePredm();
                String finalFirstNamePredm = firstNamePredm;
                Goods goods=forecastTemplate.getGoodsSet().stream().filter(g->g.getCodArtic().equals(finalFirstNamePredm)).findFirst().orElse(null);
                if(goods==null) break;
                while (true){
                    goods.addGoodsMove(moveInMapper.toGoodsMove(dto));
                    if(!prQGoodsMoveIn.isEmpty())
                        dto=prQGoodsMoveIn.poll();
                    else{ dto=null; break ;}
                    if(!firstNamePredm.equals(dto.NamePredm())) break;
                }
        }
        return forecastTemplate;
    }

@Transactional
    public ForecastTemplate saveListOfRestForForecast(ForecastTemplate forecastTemplate) throws NotEnoughData {
        if(forecastTemplate.getGoodsSet().isEmpty()) throw new NotEnoughData("Not goods to compile for requesting rest from external database ");
        List<Long> idStockList=forecastTemplate.getSetStockTTSet().stream().map(SetStockTT::getIdStock).toList();
        if (idStockList == null) throw new NotEnoughData("Not specified stock to compile for requesting rest from external database ");
        List<String> codeArticList=forecastTemplate.getGoodsSet().stream().map(Goods::getCodArtic).toList();
        List<RestDtoIn> restDtoInList = restDao.getRestByGoodsAndStockList(new GetDataByGoodsListAndStockListDtoOut(codeArticList,idStockList));
        if (restDtoInList == null) return forecastTemplate;
        TreeMap<String, List<Rest>> restMap= new TreeMap<>();
        restDtoInList.forEach(r->{
            if(restMap.containsKey(r.codArtc()))
                restMap.get(r.codArtc()).add(restMapper.toRest(r));
            else
                restMap.put(r.codArtc(),new ArrayList<>(List.of(restMapper.toRest(r))));
        });
        forecastTemplate.getGoodsSet().forEach(g->restMap.get(g.getCodArtic()).forEach(g::addRest));
        return forecastTemplate;
    }

    @Transactional
    public ForecastTemplate saveStockParam(ForecastTemplate forecastTemplate) throws NotEnoughData {
        if(forecastTemplate.getGoodsSet().isEmpty()) throw new NotEnoughData("Not goods to compile for requesting stockParam from external database ");
        List<Long> idStockList=listStockOnlyWithTip(forecastTemplate.getSetStockTTSet());
         //       forecastTemplate.getSetStockTTSet().stream().filter(set -> !set.getStockTipSaleSet().isEmpty()).map(SetStockTT::getIdStock).toList();
        if (idStockList == null) throw new NotEnoughData("Not specified stock to compile for requesting stockParam from external database ");
        // List<String> codeArticList=forecastTemplate.getGoodsSet().stream().map(Goods::getCodArtic).toList();
        List<StockParamDtoIn> stockParamDtoIns = stockParamDao.getStockParamByGoodsAndStockList(
                new GetDataByGoodsListAndStockListDtoOut(getCodeGoods(forecastTemplate.getGoodsSet()),idStockList));
        if (stockParamDtoIns == null) return forecastTemplate;
        TreeMap<String, List<StockParam>> paramMap= new TreeMap<>();
        stockParamDtoIns.forEach(p->{
            if(paramMap.containsKey(p.codArtc()))
                paramMap.get(p.codArtc()).add(stockParamMapper.toStockParam(p));
            else
                paramMap.put(p.codArtc(),new ArrayList<>(List.of(stockParamMapper.toStockParam(p))));
        });
        forecastTemplate.getGoodsSet().forEach(g->paramMap.get(g.getCodArtic()).forEach(g::addStockParam));
        return forecastTemplate;
    }



    private List<String> getCodeGoods(Set<Goods> goodsSet){
        return goodsSet.stream().map(Goods::getCodArtic).toList();
    }

    private List<Long> listStockOnlyWithTip(Set<SetStockTT> setST){
        return setST.stream().filter(set -> !set.getStockTipSaleSet().isEmpty()).map(SetStockTT::getIdStock)
                .toList();
    }
}
