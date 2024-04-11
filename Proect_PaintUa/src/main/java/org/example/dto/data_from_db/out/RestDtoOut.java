package org.example.dto.data_from_db.out;

public record RestDtoOut(
        Long id,
        long idStock,
        double rezKolich,
        double konKolich
) {
}
