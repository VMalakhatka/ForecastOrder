package org.example.mapper.data.from.db.in;

import org.example.dto.dataFromDb.in.MoveDtoIn;
import org.example.entity.data.from.db.GoodsMove;
import org.example.entity.enums.TypDocmPr;
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
