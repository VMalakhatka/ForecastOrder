package org.example.dto.data_from_db.in;

public record AssembleDtoIn(
        String parentCode,
        String childCode,
        double quntity
) { }
