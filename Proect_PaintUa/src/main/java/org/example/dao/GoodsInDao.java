package org.example.dao;

import org.example.dto.data_from_db.in.GoodsDtoIn;

import java.util.List;

public interface GoodsInDao {
    List<GoodsDtoIn> getGoodsBySupplierAndStockId(String supplier, long idStock);
}
