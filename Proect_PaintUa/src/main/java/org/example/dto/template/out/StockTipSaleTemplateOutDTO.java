package org.example.dto.template.out;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import org.example.entity.templates.entity_enum.TypDocmPr;
@Schema(description = "Вывод сохраненых шаблонов - информация для конкретноо склада - какие документы на нем участвуют в статистике ")
public record StockTipSaleTemplateOutDTO(
        Long id,
        String orgPredm,
        TypDocmPr typdocmPr,
        String vidDoc,
        boolean isEqual
) {
}
