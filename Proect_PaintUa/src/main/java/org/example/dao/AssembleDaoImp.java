package org.example.dao;

import org.example.config.RabbitConfig;
import org.example.dto.data_from_db.in.AssembleDtoIn;
import org.example.dto.data_from_db.out.GetDataByGoodsListAndStockListDtoOut;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssembleDaoImp implements AssembleDao {

    private final AmqpTemplate template;

    @Autowired
    public AssembleDaoImp(AmqpTemplate template) {
        this.template = template;
    }

    @Override
    public List<AssembleDtoIn> getAssembleByGoodsList(GetDataByGoodsListAndStockListDtoOut dto, Object o) {
        ParameterizedTypeReference<List<AssembleDtoIn>> responseType = new ParameterizedTypeReference<List<AssembleDtoIn>>() {};
        List<AssembleDtoIn> stockParamDtoIns=template.convertSendAndReceiveAsType(RabbitConfig.QUEUE_FOR_ASSEMBLE,dto,responseType);
        return stockParamDtoIns;
    }
}
