package org.example.rabbitMQ.consumer;

import org.example.config.RabbitConfig;
import org.example.dto.dataFromDb.in.MoveDtoIn;
import org.example.dto.dataFromDb.out.GeMoveByGoodsListAndStockListAndDataStartEndDtoOut;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MoveInDaoImpl implements MoveInDao{

    private final AmqpTemplate template;

    @Autowired
    public MoveInDaoImpl(AmqpTemplate template) {
        this.template = template;
    }

    @Override
    public List<MoveDtoIn> getMoveByGoodsListAndStockList(GeMoveByGoodsListAndStockListAndDataStartEndDtoOut getMove) {
        ParameterizedTypeReference<List<MoveDtoIn>> responseType = new ParameterizedTypeReference<>() {};
        return template.convertSendAndReceiveAsType(RabbitConfig.QUEUE_FOR_MOVE,getMove,responseType);
    }

}
