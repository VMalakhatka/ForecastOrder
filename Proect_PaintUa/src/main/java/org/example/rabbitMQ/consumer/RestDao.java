package org.example.rabbitMQ.consumer;

import org.example.dto.dataFromDb.in.RestDtoIn;
import org.example.dto.dataFromDb.out.GetDataByGoodsListAndStockListDtoOut;

import java.util.List;

public interface RestDao {
    List<RestDtoIn> getRestByGoodsAndStockList(GetDataByGoodsListAndStockListDtoOut dto);
}
