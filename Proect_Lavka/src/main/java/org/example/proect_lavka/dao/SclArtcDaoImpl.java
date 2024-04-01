package org.example.proect_lavka.dao;

import org.example.proect_lavka.dao.mapper.SclArtcMapper;
import org.example.proect_lavka.dao.mapper.SclRestMapper;
import org.example.proect_lavka.dao.mapper.StockParamMapper;
import org.example.proect_lavka.dto.RestDtoOut;
import org.example.proect_lavka.dto.StockParamDtoOut;
import org.example.proect_lavka.entity.SclArtc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SclArtcDaoImpl implements SclArtcDao{

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public SclArtcDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
  //      System.out.println(getAllBySupplierAndStockId("Kreul",1));
    }

    @Override
    public List<SclArtc> getAllBySupplierAndStockId(String supplier, long idStock) {
//        return jdbcTemplate.query("SELECT * FROM SCL_ARTC WHERE DOP2_ARTIC=? AND ID_SCLAD=?;",
//              new SclArtcMapper(),supplier,idStock);
        return jdbcTemplate.query("SELECT COD_ARTIC,NAME_ARTIC,CENA_VALT,COD_VALT,KON_KOLCH,REZ_KOLCH," +
                        "EDIN_IZMER,EDN_V_UPAK,DOP2_ARTIC,DOP3_ARTIC,MIN_TVRZAP,MAX_TVRZAP,ID_SCLAD,BALL1," +
                        "BALL2,BALL4,BALL5,TIP_TOVR  FROM SCL_ARTC WHERE DOP2_ARTIC=? AND ID_SCLAD=?;",
                new SclArtcMapper(),supplier,idStock);
    }

    @Override
    public List<RestDtoOut> getRestByGoodsListAndStockList(List<String> namePredmList, List<Long> idList) {
        String idParams = String.join(",", idList.stream().map(String::valueOf).toList());
        String namePredmParams = String.join("','", namePredmList);
        String sqlQuery = "SELECT COD_ARTIC,ID_SCLAD,REZ_KOLCH,KON_KOLCH FROM SCL_ARTC WHERE COD_ARTIC IN ('" + namePredmParams + "') AND ID_SCLAD IN (" + idParams + ");";
//TODO maximum length of query?
        return jdbcTemplate.query(sqlQuery,new SclRestMapper());
    }

    @Override
    public List<StockParamDtoOut> getStockParamByGoodsListAndStockList(List<String> namePredmList, List<Long> idList) {
        String idParams = String.join(",", idList.stream().map(String::valueOf).toList());
        String namePredmParams = String.join("','", namePredmList);
        String sqlQuery = "SELECT COD_ARTIC,ID_SCLAD,MIN_TVRZAP,MAX_TVRZAP,TIP_TOVR,BALL5 FROM SCL_ARTC WHERE COD_ARTIC IN ('" + namePredmParams + "') AND ID_SCLAD IN (" + idParams + ");";
//TODO maximum length of query?
        return jdbcTemplate.query(sqlQuery,new StockParamMapper());
    }
}

