package org.example.mapper.data.from.db.in;

import org.example.dto.dataFromDb.in.RestDtoIn;
import org.example.entity.data.from.db.Rest;
import org.springframework.stereotype.Component;

@Component
public class RestMapper {
    public Rest toRest(RestDtoIn dto){
        return new Rest(null,dto.idStock(), dto.rezKolich(), dto.konKolich());
    }
}
