package org.example.dto.dataFromDb.out;

import java.util.List;

public record GetDataByGoodsListAndStockListDtoOut(
        List<String> namePredmList,
        List<Long> idList
) { }
