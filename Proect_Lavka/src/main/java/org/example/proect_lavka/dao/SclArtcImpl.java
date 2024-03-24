package org.example.proect_lavka.dao;

import org.example.proect_lavka.dao.mapper.SclArtcMapper;
import org.example.proect_lavka.entity.SclArtc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SclArtcImpl implements SclArtcDao{

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public SclArtcImpl(JdbcTemplate jdbcTemplate) {
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
}
