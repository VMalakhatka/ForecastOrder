package org.example.dto.data_from_db.out;

public record StockParamDtoOut(
        Long id,
        long idStock,
        double minTvrZap,
        double maxTvrZap,
        int tipTov,
        int tipOrder
) {
}
