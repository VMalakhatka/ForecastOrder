package org.example.proect_lavka.dao;

import org.example.proect_lavka.dto.AssembleDtoOut;

import java.util.List;

public interface AssembleDao {
    List<AssembleDtoOut> getAssembleByGoodsList(List<String> namePredmList);
}
