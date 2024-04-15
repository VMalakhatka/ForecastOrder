package org.example.rabbitMQ.consumer;

import org.example.config.RabbitConfig;
import org.example.dto.dataFromDb.in.RestDtoIn;
import org.example.dto.dataFromDb.out.GetDataByGoodsListAndStockListDtoOut;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestDaoImp implements RestDao{

    private final AmqpTemplate template;

    @Autowired
    public RestDaoImp(AmqpTemplate template) {this.template = template;}

    @Override
    public List<RestDtoIn> getRestByGoodsAndStockList(GetDataByGoodsListAndStockListDtoOut dto) {
        ParameterizedTypeReference<List<RestDtoIn>> responseType = new ParameterizedTypeReference<>() {};
        return template.convertSendAndReceiveAsType(RabbitConfig.QUEUE_FOR_REST,dto,responseType);
    }
}
