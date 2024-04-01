package org.example.dto.data_from_db.out;

import java.util.List;

public record GetDataByGoodsListAndStockListDtoOut(
        List<String> namePredmList,
        List<Long> idList
) { }
