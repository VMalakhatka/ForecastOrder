package org.example.mapper.data_from_db.in;

import org.example.dto.data_from_db.in.StockParamDtoIn;
import org.example.entity.data_from_db.StockParam;
import org.springframework.stereotype.Component;

@Component
public class StockParamMapper {
    public StockParam toStockParam(StockParamDtoIn dto){
        return new StockParam(null, dto.idStock(), dto.minTvrZap(), dto.maxTvrZap(),dto.tipTov(), dto.tipOrder());
    }
}
