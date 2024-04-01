package org.example.proect_lavka.client_rabbit;


import org.example.proect_lavka.config.RabbitConfig;
import org.example.proect_lavka.dao.SclArtcDao;
import org.example.proect_lavka.dao.SclMoveDao;
import org.example.proect_lavka.dto.*;
import org.example.proect_lavka.entity.SclArtc;
import org.example.proect_lavka.entity.SclMove;
import org.example.proect_lavka.mapper.GoodsMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableRabbit
public class GoodsClient {

   SclArtcDao sclArtcDao;
   SclMoveDao sclMoveDao;
   GoodsMapper goodsMapper;
    @Autowired
    public GoodsClient(SclArtcDao sclArtcDao, SclMoveDao sclMoveDao, GoodsMapper goodsMapper) {
        this.sclArtcDao = sclArtcDao;
        this.sclMoveDao = sclMoveDao;
        this.goodsMapper = goodsMapper;
    }


    public List<SclArtc> getGoodsBySupplierAndStockId(String supp, long id){
        return sclArtcDao.getAllBySupplierAndStockId(supp,id);
    }

    public List<SclMove> getMoveByNameGoodsAndStockId(String nameArtc, int id,String strStart, String strEnd){
        return sclMoveDao.getMoveByGoodsAndData(nameArtc,id ,strStart,strEnd);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public List<GoodsDtoOut> getGoodsBySupplierAndStockId(@Payload SuppStockDtoIn suppStock){
        return sclArtcDao.getAllBySupplierAndStockId(suppStock.supplier(),suppStock.idStock()).stream().map(g->
                goodsMapper.toGoodsDtoOut(g)).collect(Collectors.toList());
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_FOR_REST)
    public List<RestDtoOut> getRestByGoodsAndStockList(@Payload GetDataByGoodsListAndStockListDtoIn goodsListAndStockListDtoInksList){
        return sclArtcDao.getRestByGoodsListAndStockList(goodsListAndStockListDtoInksList.namePredmList(),
                goodsListAndStockListDtoInksList.idList());
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_FOR_SOCK_PARAM)
    public List<StockParamDtoOut> getStockParamByGoodsAndStockList(@Payload GetDataByGoodsListAndStockListDtoIn goodsListAndStockListDtoInksList){
        return sclArtcDao.getStockParamByGoodsListAndStockList(goodsListAndStockListDtoInksList.namePredmList(),
                goodsListAndStockListDtoInksList.idList());
    }
}
