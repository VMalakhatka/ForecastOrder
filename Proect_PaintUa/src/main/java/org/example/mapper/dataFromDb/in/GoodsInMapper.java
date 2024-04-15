package org.example.mapper.dataFromDb.in;

import org.example.dto.dataFromDb.in.GoodsDtoIn;
import org.example.entity.dataFromDb.Goods;
import org.example.entity.forecast.Forecast;
import org.springframework.stereotype.Component;

@Component
public class GoodsInMapper {
    public Goods toGoodsEntity(GoodsDtoIn dto){
        Goods goods=new Goods();
        goods.setForecast(new Forecast(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0));
        goods.setCodArtic(dto.codArtic());
        goods.setNameArtic(dto.nameArtic());
        goods.setUchetCena(dto.uchetCena());
        goods.setCenaValt(dto.cenaValt());
        goods.setCodValt(dto.codValt());
        goods.setEdnVUpak(dto.ednVUpak());
        goods.setSupplier(dto.supplier());
        goods.setFrost(dto.frost());
        goods.setAssembl(dto.assembl());
        return goods;
    }
}
