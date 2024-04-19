package org.example.mapper.data.from.db.in;

import org.example.dto.dataFromDb.in.StockParamDtoIn;
import org.example.entity.data.from.db.StockParam;
import org.springframework.stereotype.Component;

@Component
public class StockParamMapper {
    public StockParam toStockParam(StockParamDtoIn dto){
        return new StockParam(null, dto.idStock(), dto.minTvrZap(), dto.maxTvrZap(),dto.tipTov(), dto.tipOrder());
    }
}
