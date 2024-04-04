package org.example.dao;

import org.example.config.RabbitConfig;
import org.example.dto.data_from_db.in.GoodsDtoIn;
import org.example.dto.data_from_db.out.getGoodsDtoOut;
import org.example.exception.RabbitNotAnswerException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoodsInDaoImpl implements GoodsInDao{

    private final AmqpTemplate template;

    @Autowired
    public GoodsInDaoImpl(AmqpTemplate template) {
        this.template = template;
    }

    @Override
    public List<GoodsDtoIn> getGoodsBySupplierAndStockId(String typeOfForecast,String supplier,long idStock) throws RabbitNotAnswerException {
        ParameterizedTypeReference<Boolean> healsType = new ParameterizedTypeReference<Boolean>() {};
        Boolean goodHeals=template.convertSendAndReceiveAsType(RabbitConfig.QUEUE_HEALS, "Yow are your?",healsType);
        if(goodHeals==null) throw new RabbitNotAnswerException(" RabbitMQ not answer ");
        ParameterizedTypeReference<List<GoodsDtoIn>> responseType = new ParameterizedTypeReference<List<GoodsDtoIn>>() {};
        return template.convertSendAndReceiveAsType(RabbitConfig.QUEUE_NAME,
                new getGoodsDtoOut(typeOfForecast,supplier,idStock),responseType);
    }
}
