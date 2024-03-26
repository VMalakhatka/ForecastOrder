package org.example.dao;

import org.example.config.RabbitConfig;
import org.example.dto.data_from_db.in.GoodsDtoIn;
import org.example.dto.data_from_db.out.SuppStockDtoOut;
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
    public List<GoodsDtoIn> getGoodsBySupplierAndStockId(String supplier,long idStock) {
        ParameterizedTypeReference<List<GoodsDtoIn>> responseType = new ParameterizedTypeReference<List<GoodsDtoIn>>() {};
        List<GoodsDtoIn> goodsDtoInList=template.convertSendAndReceiveAsType(RabbitConfig.QUEUE_NAME,new SuppStockDtoOut(supplier,idStock),responseType);
        return goodsDtoInList;
    }
}
