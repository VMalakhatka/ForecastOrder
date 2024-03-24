package org.example.dto.data_from_db.out;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
@Schema(description = "In the output of goods the reference to ForecastTemplate is " +
        "forecastTemplateId, GoodsMove - is not displayed this information is considered " +
        "additional for viewing the analysis and is possible by additional request - because it can be large" +
        "There is a restTT field that shows the total balance of all warehouses that participate in the analysis.")
public record GoodsDtoOut(
        Long id,
        String codArtic,
        String nameArtic,
        double uchetCena,
        double cenaValt,
        String codValt,
        double ednVUpak,
        String supplier,
        boolean frost,
        int assembl,
        double notOnStock,
        double notSaleAndSale,
        double sale,
        double restTT,
        double needToMove,
        double optForecast,
        double orderWithoutPack,
        double orderTT,
        long forecastTemplateId,
        List<StockParamDtoOut> stockParamDtoOuts,
        List<AssembleDtoOut> assembleParentDtoOuts,
        AssembleDtoOut assembleChildDtoOut
        ) {
}
