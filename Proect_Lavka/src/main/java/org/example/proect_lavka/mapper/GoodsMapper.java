package org.example.proect_lavka.mapper;

import org.example.proect_lavka.dto.GoodsDtoOut;
import org.example.proect_lavka.entity.SclArtc;
import org.springframework.stereotype.Component;

@Component
public class GoodsMapper {
    public GoodsDtoOut toGoodsDtoOut(SclArtc goods){
        return new GoodsDtoOut(
                goods.codArtic(),
                goods.nameArtic(),
                0.0,
                goods.cenaValt(),
                goods.codValt(),
                goods.ednVUpak(),
                goods.dop2Artic(),
                goods.ball2() == 1.0,
                (int) goods.ball4());
    }
}
