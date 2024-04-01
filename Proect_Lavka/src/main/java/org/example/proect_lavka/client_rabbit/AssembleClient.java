package org.example.proect_lavka.client_rabbit;


import org.example.proect_lavka.config.RabbitConfig;
import org.example.proect_lavka.dao.AssembleDao;
import org.example.proect_lavka.dao.mapper.AssembleMapper;
import org.example.proect_lavka.dto.AssembleDtoOut;
import org.example.proect_lavka.dto.GetDataByGoodsListAndStockListDtoIn;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableRabbit
public class AssembleClient {

  AssembleDao assembleDao;
  AssembleMapper assembleMapper;
    @Autowired
    public AssembleClient(AssembleDao assembleDao, AssembleMapper assembleMapper) {
        this.assembleDao = assembleDao;
        this.assembleMapper = assembleMapper;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_FOR_ASSEMBLE)
    public List<AssembleDtoOut> getAssembleByGoodsList(@Payload GetDataByGoodsListAndStockListDtoIn goodsListAndStockListDtoInksList){
        return assembleDao.getAssembleByGoodsList(goodsListAndStockListDtoInksList.namePredmList());
    }
}
