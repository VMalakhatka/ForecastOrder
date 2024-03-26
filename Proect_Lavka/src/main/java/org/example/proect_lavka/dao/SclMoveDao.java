package org.example.proect_lavka.dao;

import org.example.proect_lavka.entity.SclMove;

import java.util.Date;
import java.util.List;

public interface SclMoveDao {
    List<SclMove> getMoveByGoodsAndData(String NamePredm, int id,String start, String end);
}
