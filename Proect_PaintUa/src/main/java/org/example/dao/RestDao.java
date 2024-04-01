package org.example.dao;

import org.example.dto.data_from_db.in.RestDtoIn;
import org.example.dto.data_from_db.out.GetDataByGoodsListAndStockListDtoOut;

import java.util.List;

public interface RestDao {
    List<RestDtoIn> getRestByGoodsAndStockList(GetDataByGoodsListAndStockListDtoOut dto);
}
