package org.example.mapper.dataFromDb.in;

import org.example.dto.dataFromDb.in.StockParamDtoIn;
import org.example.entity.dataFromDb.StockParam;
import org.springframework.stereotype.Component;

@Component
public class StockParamMapper {
    public StockParam toStockParam(StockParamDtoIn dto){
        return new StockParam(null, dto.idStock(), dto.minTvrZap(), dto.maxTvrZap(),dto.tipTov(), dto.tipOrder());
    }
}
