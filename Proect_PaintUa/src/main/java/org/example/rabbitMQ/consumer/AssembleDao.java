package org.example.rabbitMQ.consumer;

import org.example.dto.dataFromDb.in.AssembleDtoIn;
import org.example.dto.dataFromDb.out.GetDataByGoodsListAndStockListDtoOut;

import java.util.List;

public interface AssembleDao {
    List<AssembleDtoIn> getAssembleByGoodsList(GetDataByGoodsListAndStockListDtoOut dto, Object o);
}
