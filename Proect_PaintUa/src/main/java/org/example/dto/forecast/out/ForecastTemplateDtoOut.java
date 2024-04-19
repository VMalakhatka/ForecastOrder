package org.example.dto.forecast.out;

import org.example.dto.dataFromDb.out.GoodsDtoOut;
import org.example.entity.enums.TypeOfForecast;

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
