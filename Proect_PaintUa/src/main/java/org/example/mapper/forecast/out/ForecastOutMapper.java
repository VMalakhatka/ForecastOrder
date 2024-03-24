package org.example.mapper.forecast.out;

import org.example.dto.forecast.out.ForecastTemplateDtoOut;
import org.example.dto.forecast.out.SetStockTtDtoOut;
import org.example.dto.forecast.out.StockTipSaleOutDTO;
import org.example.entity.forecast.ForecastTemplate;
import org.example.entity.forecast.SetStockTT;
import org.example.entity.forecast.StockTipSale;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ForecastOutMapper {

    public ForecastTemplateDtoOut toForecastTemplateDtoOut(ForecastTemplate fT){
        return new ForecastTemplateDtoOut(fT.getId(), fT.getStartAnalysis(), fT.getEndAnalysis(), fT.getOrderForDay(),
                fT.getName(), fT.getType(),fT.getKoefToRealSale(), fT.getSupplier(),
                fT.getSetStockTTSet().stream().map(this::toSetStockTtDtoOut).collect(Collectors.toList()),
                null);
    }
    public SetStockTtDtoOut toSetStockTtDtoOut(SetStockTT set){
        return new SetStockTtDtoOut(set.getId(), set.getIdTT(), set.getNameTT(),
                set.getIdStock(), set.getNameStock(), set.getRole(), set.isMin(), set.isMax(), set.isFasovka(),
                set.getStockTipSaleSet().stream().map(this::toStockTipSaleOutDTO).collect(Collectors.toList()));
    }

    public StockTipSaleOutDTO toStockTipSaleOutDTO(StockTipSale tip){
        return new StockTipSaleOutDTO(tip.getId(), tip.getOrgPredm(), tip.getTypdocmPr(),
                tip.getVidDoc(), tip.isEqual());
    }
}




