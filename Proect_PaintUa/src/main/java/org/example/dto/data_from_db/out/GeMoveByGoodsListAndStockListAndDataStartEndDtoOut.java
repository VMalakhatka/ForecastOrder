package org.example.dto.data_from_db.out;

import java.util.List;

public record GeMoveByGoodsListAndStockListAndDataStartEndDtoOut(
        List<String> namePredmList,
        List<Long> idList,
        String start,
        String end
) { }
