package org.example.dto.template.out;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.entity.entity_enum.StockRole;

import java.util.List;
@Schema(description = "Вывод сохраненых шаблонов - информация о складе в шаблоне его роль ")
public record SetStockTtTemplateOutDTO(
        Long id,
        long idTT,
        String nameTT,
        long idStock,
        String nameStock,
        StockRole role,
        boolean isMin, boolean isMax, boolean isFasovka,
        List<StockTipSaleTemplateOutDTO> stockTipSaleTemplateOutDTOS
) {
}
