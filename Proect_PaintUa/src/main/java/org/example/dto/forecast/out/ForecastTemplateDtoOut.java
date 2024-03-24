package org.example.dto.forecast.out;

import org.example.dto.data_from_db.out.GoodsDtoOut;
import org.example.entity.forecast.entity_enum.TypeOfForecast;

import java.time.LocalDateTime;
import java.util.List;

public record ForecastTemplateDtoOut(
        Long id,
       LocalDateTime startAnalysis,
        LocalDateTime endAnalysis,
        int orderForDay,
        String name,
        TypeOfForecast type,
        double koefToRealSale,
        String supplier,
        List<SetStockTtDtoOut> setStockTtDtoOuts,
        List<GoodsDtoOut> goodsDtoOuts
) {
}
