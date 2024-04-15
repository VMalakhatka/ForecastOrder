package org.example.mapper.dataFromDb.in;

import org.example.dto.dataFromDb.in.RestDtoIn;
import org.example.entity.dataFromDb.Rest;
import org.springframework.stereotype.Component;

@Component
public class RestMapper {
    public Rest toRest(RestDtoIn dto){
        return new Rest(null,dto.idStock(), dto.rezKolich(), dto.konKolich());
    }
}
