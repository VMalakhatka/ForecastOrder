package org.example.dto.data_from_db.in;

import jakarta.persistence.Column;

public record RestDtoIn(
        String codArtc,
        long idStock,
        double rezKolich,
        double konKolich
) { }
