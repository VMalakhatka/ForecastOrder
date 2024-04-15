package org.example.dto.dataFromDb.out;

public record RestDtoOut(
        Long id,
        long idStock,
        double rezKolich,
        double konKolich
) {
}
