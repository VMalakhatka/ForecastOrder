package org.example.dao;

import org.example.dto.data_from_db.in.AssembleDtoIn;
import org.example.dto.data_from_db.out.GetDataByGoodsListAndStockListDtoOut;

import java.util.List;

public interface AssembleDao {
    List<AssembleDtoIn> getAssembleByGoodsList(GetDataByGoodsListAndStockListDtoOut dto);
}
