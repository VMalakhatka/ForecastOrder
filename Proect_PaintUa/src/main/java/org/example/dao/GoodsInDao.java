package org.example.dao;

import org.example.dto.data_from_db.in.GoodsDtoIn;
import org.example.exeption.RabbitNotAnswer;

import java.net.ConnectException;
import java.util.List;

public interface GoodsInDao {
    List<GoodsDtoIn> getGoodsBySupplierAndStockId(String typeOfForecast,String supplier, long idStock) throws ConnectException, RabbitNotAnswer;
}
