package org.example.service;

import org.example.dao.GoodsInDao;
import org.example.dto.data_from_db.in.GoodsDtoIn;
import org.example.entity.data_from_db.Goods;
import org.example.entity.forecast.ForecastTemplate;
import org.example.entity.forecast.SetStockTT;
import org.example.entity.templates.entity_enum.StockRole;
import org.example.exeption.DataNotValid;
import org.example.exeption.NotEnoughData;
import org.example.mapper.data_from_db.in.GoodsInMapper;
import org.example.repository.data_from_db.GoodsRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FromExternalDatabaseServise {

    GoodsInDao goodsInDao;
    GoodsInMapper goodsInMapper;
    GoodsRepository goodsRepository;

    @Autowired
    public FromExternalDatabaseServise(GoodsInDao goodsInDao, GoodsInMapper goodsInMapper, GoodsRepository goodsRepository) {
        this.goodsInDao = goodsInDao;
        this.goodsInMapper = goodsInMapper;
        this.goodsRepository = goodsRepository;
    }




    public ForecastTemplate saveListOfGoodsBySupplier(ForecastTemplate forecastTemplate) throws NotEnoughData, DataNotValid {
        if(!forecastTemplate.getGoodsSet().isEmpty()) throw new DataNotValid("The product set in this forecast is not empty You cannot load new data into this forecast. ");
        SetStockTT setStockTT=forecastTemplate.getSetStockTTSet().stream().
                filter(set -> !set.getStockTipSaleSet().isEmpty()).findFirst().orElseThrow(()->
                        new NotEnoughData("Not specified stock to compile for requesting data from external database "));
        List<GoodsDtoIn> goodsDtoInList = goodsInDao.getGoodsBySupplierAndStockId(forecastTemplate.getSupplier(),setStockTT.getIdStock());
        goodsDtoInList.forEach(dto -> forecastTemplate.addGoods(goodsInMapper.toGoodsEntity(dto)));
        //TODO flash?
        return forecastTemplate;
    }

    public ForecastTemplate saveListOfChildForForecast(ForecastTemplate forecastTemplate) {
        return null;
    }

    public ForecastTemplate saveListOfMoveForForecast(ForecastTemplate forecastTemplate) {
        return null;
    }

    public ForecastTemplate saveListOfRestForForecast(ForecastTemplate forecastTemplate) {
        return null;
    }

    public ForecastTemplate saveStockParam(ForecastTemplate forecastTemplate){
        return null;
    }


}
