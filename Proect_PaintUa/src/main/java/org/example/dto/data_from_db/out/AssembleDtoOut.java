package org.example.dto.data_from_db.out;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
@Schema(description = "Contain only Id items")
public record AssembleDtoOut(
        Long id,
        double quantity,
        Long parentGoodId,
        Long childGoodId
) {
}
