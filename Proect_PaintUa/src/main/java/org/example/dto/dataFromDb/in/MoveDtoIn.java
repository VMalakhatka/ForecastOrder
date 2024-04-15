package org.example.dto.dataFromDb.in;

import java.time.LocalDateTime;

public record MoveDtoIn(
        String NamePredm,
    double UnicumNum,
    long nDocum,
    String  numdcmDop,
    String orgPredm,
    LocalDateTime data,
    double quantity,
    String typDocmPr,
    String vidDoc,
    long idStock
) { }
