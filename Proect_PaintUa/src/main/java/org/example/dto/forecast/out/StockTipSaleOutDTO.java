package org.example.dto.forecast.out;

import org.example.entity.templates.entity_enum.TypDocmPr;

public record StockTipSaleOutDTO(
        Long id,
        String orgPredm,
        TypDocmPr typdocmPr,
        String vidDoc,
        boolean isEqual
) {

}
