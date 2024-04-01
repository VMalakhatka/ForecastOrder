package org.example.mapper.data_from_db.in;

import org.example.dto.data_from_db.in.RestDtoIn;
import org.example.entity.data_from_db.Rest;
import org.springframework.stereotype.Component;

@Component
public class RestMapper {
    public Rest toRest(RestDtoIn dto){
        return new Rest(null,dto.idStock(), dto.rezKolich(), dto.konKolich());
    }
}
