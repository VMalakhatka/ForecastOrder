package org.example.dto.data_from_db.in;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for receive from another base")
public record GoodsDtoIn(
        String codArtic,
        String nameArtic,
        double uchetCena,
        double cenaValt,
        String codValt,
        double ednVUpak,
        String supplier,
        boolean frost,
        int assembl
        ) {
}
