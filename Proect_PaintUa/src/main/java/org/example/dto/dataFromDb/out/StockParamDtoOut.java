package org.example.dto.dataFromDb.out;

public record StockParamDtoOut(
        Long id,
        long idStock,
        double minTvrZap,
        double maxTvrZap,
        int tipTov,
        int tipOrder
) {
}
