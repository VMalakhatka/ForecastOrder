package org.example.proect_lavka.mapper;

import org.example.proect_lavka.dto.MoveDtoOut;
import org.example.proect_lavka.entity.SclMove;
import org.example.proect_lavka.entity.entity_enum.TypDocmPrOut;
import org.springframework.stereotype.Component;

@Component
public class MoveMapper {
    public MoveDtoOut toMoveDtoOut(SclMove sclMove){
        return new MoveDtoOut(
                sclMove.NamePredm(),
                sclMove.UnicumNum(),
                sclMove.nDocum(),
                sclMove.numdcmDop(),
                sclMove.orgPredm(),
                sclMove.data().toLocalDateTime(),
                sclMove.quantity(),
                TypDocmPrOut.fromString(sclMove.typDocmPr()),
                sclMove.vidDoc(),
                sclMove.idStock()
        );
    }
}
