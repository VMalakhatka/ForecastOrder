package org.example.dao;

import org.example.dto.data_from_db.in.MoveDtoIn;
import org.example.dto.data_from_db.out.GeMoveByGoodsListAndStockListAndDataStartEndDtoOut;

import java.util.List;

public interface MoveInDao {
    List<MoveDtoIn> getMoveByGoodsListAndStockList(GeMoveByGoodsListAndStockListAndDataStartEndDtoOut getMove);
}
