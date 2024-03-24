package org.example.dto.forecast.out;

import org.example.dto.template.out.StockTipSaleTemplateOutDTO;
import org.example.entity.templates.entity_enum.StockRole;

import java.util.List;

public record SetStockTtDtoOut(
        Long id,
        long idTT,
        String nameTT,
        long idStock,
        String nameStock,
        StockRole role,
        boolean isMin, boolean isMax, boolean isFasovka,
        List<StockTipSaleOutDTO>  stockTipSaleOutDTOS
) {
}
