package org.example.proect_lavka.service;

import org.example.proect_lavka.dao.SclArtcDao;
import org.example.proect_lavka.entity.SclArtc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

   SclArtcDao sclArtcDao;
    @Autowired
    public GoodsService(SclArtcDao sclArtcDao) {
        this.sclArtcDao = sclArtcDao;
    }

    public List<SclArtc> getGoodsBySupplierAndStockId(String supp, long id){
        return sclArtcDao.getAllBySupplierAndStockId(supp,id);
    }
}
