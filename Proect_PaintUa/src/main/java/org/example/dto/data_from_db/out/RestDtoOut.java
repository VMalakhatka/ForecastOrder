package org.example.dto.data_from_db.out;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import org.example.entity.data_from_db.Goods;

public record RestDtoOut(
        Long id,
        long idStock,
        double rezKolich,
        double konKolich
) {
}
