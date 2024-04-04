package org.example.proect_lavka.dao;

import org.example.proect_lavka.dto.RestDtoOut;
import org.example.proect_lavka.dto.StockParamDtoOut;
import org.example.proect_lavka.entity.SclArtc;

import java.util.List;

public interface SclArtcDao {
  List<SclArtc> getAllBySupplierAndStockId(String supplier, long idStock);

  List<RestDtoOut> getRestByGoodsListAndStockList(List<String> namePredmList, List<Long> idList);

  List<StockParamDtoOut> getStockParamByGoodsListAndStockList(List<String> namePredmList, List<Long> idList);

    List<SclArtc> getGoodsByNumDoc(long numDoc);
}
