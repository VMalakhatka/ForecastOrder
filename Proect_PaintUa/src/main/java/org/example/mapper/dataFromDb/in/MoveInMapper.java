package org.example.mapper.dataFromDb.in;

import org.example.dto.dataFromDb.in.MoveDtoIn;
import org.example.entity.dataFromDb.GoodsMove;
import org.example.entity.entityEnum.TypDocmPr;
import org.springframework.stereotype.Component;

@Component
public class MoveInMapper {


    public GoodsMove toGoodsMove(MoveDtoIn dto){
        return new GoodsMove(
                null, dto.idStock(), dto.UnicumNum(), dto.nDocum(),
                dto.numdcmDop()
                //"  "
                , dto.orgPredm(),
                dto.data(), dto.quantity(),
                0,
                TypDocmPr.valueOf(dto.typDocmPr()), dto.vidDoc());
    }
}
