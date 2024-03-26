//    package org.example.service;
//
//
//import org.example.dao.GoodsInDao;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.List;
//
//@Service
//public class GoodsFomDbService {
//  GoodsInDao goodsInDao;
//
//    @Autowired
//    public GoodsFomDbService(GoodsInDao goodsInDao) {
//        this.goodsInDao = goodsInDao;
//    }
//
//    public List<GoodsInDao> getGoodsBySupplierAndStockId(String supplier, long idStock){
//      return goodsInDao.getGoodsBySupplierAndStockId(supplier,idStock);
//    }
//}
