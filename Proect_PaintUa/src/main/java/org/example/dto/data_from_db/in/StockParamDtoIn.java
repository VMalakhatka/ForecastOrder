package org.example.dto.data_from_db.in;

import jakarta.persistence.Column;

public record StockParamDtoIn(
        String codArtc,
        long idStock,
        double minTvrZap,//"MIN_TVRZAP"
        double maxTvrZap,//"MAX_TVRZAP"
        int tipTov,//"TIP_TOV"
        int tipOrder //"TIP_ORDER"
) { }
