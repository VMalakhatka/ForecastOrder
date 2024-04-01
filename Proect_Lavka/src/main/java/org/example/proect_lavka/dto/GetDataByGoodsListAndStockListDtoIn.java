package org.example.proect_lavka.dto;

import java.util.List;

public record GetDataByGoodsListAndStockListDtoIn(
        List<String> namePredmList,
        List<Long> idList
) { }
