package org.example.proect_lavka.dao;

import org.example.proect_lavka.entity.SclArtc;

import java.util.List;

public interface SclArtcDao {
  List<SclArtc> getAllBySupplierAndStockId(String supplier, long idStock);
}
