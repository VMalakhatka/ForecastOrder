package org.example.dto.template.out;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.entity.entityEnum.TypDocmPr;
@Schema(description = "Вывод сохраненых шаблонов - информация для конкретноо склада - какие документы на нем участвуют в статистике ")
public record StockTipSaleTemplateOutDTO(
        Long id,
        String orgPredm,
        TypDocmPr typdocmPr,
        String vidDoc,
        boolean isEqual
) {
}
