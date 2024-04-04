package org.example.proect_lavka.client_rabbit;


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
public class HealsClient {

    @RabbitListener(queues = RabbitConfig.QUEUE_FOR_MOVE)
    public Boolean healsResponse(@Payload String message){
        return true;
    }
}
