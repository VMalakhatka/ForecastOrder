package org.example.proect_lavka.service;


import org.example.proect_lavka.config.RabbitConfig;
import org.example.proect_lavka.dao.SclArtcDao;
import org.example.proect_lavka.dao.SclMoveDao;
import org.example.proect_lavka.dto.GoodsDtoOut;
import org.example.proect_lavka.dto.SuppStockDtoIn;
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
public class GoodsService {

   SclArtcDao sclArtcDao;
   SclMoveDao sclMoveDao;
   GoodsMapper goodsMapper;
    @Autowired
    public GoodsService(SclArtcDao sclArtcDao, SclMoveDao sclMoveDao, GoodsMapper goodsMapper) {
        this.sclArtcDao = sclArtcDao;
        this.sclMoveDao = sclMoveDao;
        this.goodsMapper = goodsMapper;
    }


    public List<SclArtc> getGoodsBySupplierAndStockId(String supp, long id){
        return sclArtcDao.getAllBySupplierAndStockId(supp,id);
    }

    public List<SclMove> getMoveByNameGoodsAndStockId(String nameArtc, int id,String strStart, String strEnd){
//        List<SclMove> sclMoves=sclMoveDao.getMoveByGoodsAndData(nameArtc,id,strStart,strEnd);
//        sclMoves.forEach(m->{
//            System.out.println(m.data().toLocalDateTime()+"   "+m);
//        });
        return sclMoveDao.getMoveByGoodsAndData(nameArtc,id ,strStart,strEnd);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public List<GoodsDtoOut> getGoodsBySupplierAndStockId(@Payload SuppStockDtoIn suppStock){
        List<GoodsDtoOut> goodsDtoOutList=sclArtcDao.getAllBySupplierAndStockId(suppStock.supplier(),suppStock.idStock()).stream().map(g->
                goodsMapper.toGoodsDtoOut(g)).collect(Collectors.toList());
        System.out.println(goodsDtoOutList);
        return goodsDtoOutList;
    }
}
