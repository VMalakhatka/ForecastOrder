package org.example.dto.dataFromDb.in;

public record AssembleDtoIn(
        String parentCode,
        String childCode,
        double quntity
) { }
