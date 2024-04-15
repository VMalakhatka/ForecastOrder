package org.example.proect_lavka.rabbitMQ.Publisher;


import org.example.proect_lavka.config.RabbitConfig;
import org.example.proect_lavka.dao.SclArtcDao;
import org.example.proect_lavka.dao.SclMoveDao;
import org.example.proect_lavka.dto.GetMoveByGoodsListAndStockListDtoIn;
import org.example.proect_lavka.dto.MoveDtoOut;
import org.example.proect_lavka.entity.SclMove;
import org.example.proect_lavka.mapper.MoveMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@EnableRabbit
public class MoveClient {

   SclArtcDao sclArtcDao;
   SclMoveDao sclMoveDao;
   MoveMapper moveMapper;
    @Autowired
    public MoveClient(SclArtcDao sclArtcDao, SclMoveDao sclMoveDao, MoveMapper moveMapper) {
        this.sclArtcDao = sclArtcDao;
        this.sclMoveDao = sclMoveDao;
        this.moveMapper = moveMapper;
    }


    public List<SclMove> getMoveByNameGoodsAndStockId(String nameArtc, int id,String strStart, String strEnd){
        return sclMoveDao.getMoveByGoodsAndData(nameArtc,id ,strStart,strEnd);
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_FOR_MOVE)
    public List<MoveDtoOut> getMoveByListOfGoodsAndData(@Payload GetMoveByGoodsListAndStockListDtoIn getMoveDto){
        return sclMoveDao.getMoveByListOfGoodsAndData(getMoveDto.namePredmList(),getMoveDto.idList(),getMoveDto.start(),getMoveDto.end())
                .stream().map(m-> moveMapper.toMoveDtoOut(m)).collect(Collectors.toList());
    }
}
