package org.example.dao;

import org.example.dto.data_from_db.in.StockParamDtoIn;
import org.example.dto.data_from_db.out.GetDataByGoodsListAndStockListDtoOut;

import java.util.List;

public interface StockParamDao {
    List<StockParamDtoIn> getStockParamByGoodsAndStockList(GetDataByGoodsListAndStockListDtoOut dtoOut);
}
