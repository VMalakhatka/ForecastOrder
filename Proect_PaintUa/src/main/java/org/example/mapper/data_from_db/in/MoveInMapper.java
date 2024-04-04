package org.example.mapper.data_from_db.in;

import org.example.dto.data_from_db.in.MoveDtoIn;
import org.example.entity.data_from_db.GoodsMove;
import org.example.entity.entity_enum.TypDocmPr;
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
