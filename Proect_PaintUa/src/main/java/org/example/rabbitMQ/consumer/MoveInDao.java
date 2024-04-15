package org.example.rabbitMQ.consumer;

import org.example.dto.dataFromDb.in.MoveDtoIn;
import org.example.dto.dataFromDb.out.GeMoveByGoodsListAndStockListAndDataStartEndDtoOut;

import java.util.List;

public interface MoveInDao {
    List<MoveDtoIn> getMoveByGoodsListAndStockList(GeMoveByGoodsListAndStockListAndDataStartEndDtoOut getMove);
}
