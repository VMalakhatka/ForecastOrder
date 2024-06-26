package org.example.dao;

import org.example.config.RabbitConfig;
import org.example.dto.data_from_db.in.StockParamDtoIn;
import org.example.dto.data_from_db.out.GetDataByGoodsListAndStockListDtoOut;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class StockParamDaoImp implements StockParamDao{

    private final AmqpTemplate template;
    @Autowired
    public StockParamDaoImp(AmqpTemplate template) {
        this.template = template;
    }

    @Override
    public List<StockParamDtoIn> getStockParamByGoodsAndStockList(GetDataByGoodsListAndStockListDtoOut dto) {
        ParameterizedTypeReference<List<StockParamDtoIn>> responseType = new ParameterizedTypeReference<List<StockParamDtoIn>>() {};
        List<StockParamDtoIn> stockParamDtoIns=template.convertSendAndReceiveAsType(RabbitConfig.QUEUE_FOR_SOCK_PARAM,dto,responseType);
        return stockParamDtoIns;
    }
}
