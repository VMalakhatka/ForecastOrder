package org.example.dto.forecast.out;

import org.example.entity.enums.TypDocmPr;

public record StockTipSaleOutDTO(
        Long id,
        String orgPredm,
        TypDocmPr typdocmPr,
        String vidDoc,
        boolean isEqual
) {

}
