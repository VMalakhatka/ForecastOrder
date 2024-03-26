package org.example.proect_lavka.dto;

public record GoodsDtoOut(
        String codArtic,
        String nameArtic,
        double uchetCena,
        double cenaValt,
        String codValt,
        double ednVUpak,
        String supplier,
        boolean frost,
        int assembl
) {}
