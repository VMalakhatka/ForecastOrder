package org.example.proect_lavka.dto;

import java.time.LocalDateTime;

public record MoveDtoOut(
        String NamePredm,
        double UnicumNum,
        long nDocum,
        String  numdcmDop,
        String orgPredm,
        LocalDateTime data,
        double quantity,
        org.example.proect_lavka.entity.entity_enum.TypDocmPrOut typDocmPr,
        String vidDoc,
        long idStock
) { }
