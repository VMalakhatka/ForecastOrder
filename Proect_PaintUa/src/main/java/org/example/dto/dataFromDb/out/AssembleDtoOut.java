package org.example.dto.dataFromDb.out;


import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Contain only Id items")
public record AssembleDtoOut(
        Long id,
        double quantity,
        Long parentGoodId,
        Long childGoodId
) {
}
