package org.example.dto.dataFromDb.in;

public record RestDtoIn(
        String codArtc,
        long idStock,
        double rezKolich,
        double konKolich
) { }
