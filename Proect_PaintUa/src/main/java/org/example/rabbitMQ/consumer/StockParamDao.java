package org.example.rabbitMQ.consumer;

import org.example.dto.dataFromDb.in.StockParamDtoIn;
import org.example.dto.dataFromDb.out.GetDataByGoodsListAndStockListDtoOut;

import java.util.List;

public interface StockParamDao {
    List<StockParamDtoIn> getStockParamByGoodsAndStockList(GetDataByGoodsListAndStockListDtoOut dtoOut);
}
