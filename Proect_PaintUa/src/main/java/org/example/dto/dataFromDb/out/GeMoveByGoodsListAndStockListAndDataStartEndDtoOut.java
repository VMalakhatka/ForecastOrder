package org.example.dto.dataFromDb.out;

import java.util.List;

public record GeMoveByGoodsListAndStockListAndDataStartEndDtoOut(
        List<String> namePredmList,
        List<Long> idList,
        String start,
        String end
) { }
