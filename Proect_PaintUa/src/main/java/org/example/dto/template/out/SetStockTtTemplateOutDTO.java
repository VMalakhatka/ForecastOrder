package org.example.dto.template.out;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.example.entity.templates.StockTipSaleTemplate;
import org.example.entity.templates.Template;
import org.example.entity.templates.entity_enum.StockRole;

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
