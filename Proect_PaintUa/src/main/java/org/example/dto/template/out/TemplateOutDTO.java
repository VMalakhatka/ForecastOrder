package org.example.dto.template.out;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import org.example.entity.templates.SetStockTtTemplate;

import java.time.LocalDateTime;
import java.util.List;
@Schema(description = "Вывод сохраненых шаблонов - это шапка с общими данными ")
public record TemplateOutDTO(
     Long id,
     LocalDateTime startAnalysis,
     LocalDateTime endAnalysis,
     int orderForDay,
     String name,
     String type,
     double koefToRealSale,
     List<SetStockTtTemplateOutDTO> setStockTtTemplateOutDTOS
) {
}
