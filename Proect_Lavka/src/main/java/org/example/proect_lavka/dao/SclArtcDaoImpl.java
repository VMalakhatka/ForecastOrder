package org.example.proect_lavka.dao;

import org.example.proect_lavka.dao.mapper.SclArtcMapper;
import org.example.proect_lavka.dao.mapper.SclRestMapper;
import org.example.proect_lavka.dao.mapper.StockParamMapper;
import org.example.proect_lavka.dto.RestDtoOut;
import org.example.proect_lavka.dto.StockParamDtoOut;
import org.example.proect_lavka.entity.SclArtc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SclArtcDaoImpl implements SclArtcDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SclArtcDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SclArtc> getAllBySupplierAndStockId(String supplier, long idStock) {
        try {
            return jdbcTemplate.query("SELECT COD_ARTIC,NAME_ARTIC,CENA_VALT,COD_VALT,KON_KOLCH,REZ_KOLCH," +
                            "EDIN_IZMER,EDN_V_UPAK,DOP2_ARTIC,DOP3_ARTIC,MIN_TVRZAP,MAX_TVRZAP,ID_SCLAD,BALL1," +
                            "BALL2,BALL4,BALL5,TIP_TOVR  FROM SCL_ARTC WHERE DOP2_ARTIC=? AND ID_SCLAD=?;",
                    new SclArtcMapper(), supplier, idStock);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<RestDtoOut> getRestByGoodsListAndStockList(List<String> namePredmList, List<Long> idList) {
        String idParams = String.join(",", idList.stream().map(String::valueOf).toList());
        String namePredmParams = String.join("','", namePredmList);

        String sqlQuery = "SELECT COD_ARTIC,ID_SCLAD,REZ_KOLCH,KON_KOLCH FROM SCL_ARTC WHERE COD_ARTIC IN ('" + namePredmParams + "') AND ID_SCLAD IN (" + idParams + ");";
        //TODO maximum length of query?
        try {
            return jdbcTemplate.query(sqlQuery, new SclRestMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<StockParamDtoOut> getStockParamByGoodsListAndStockList(List<String> namePredmList, List<Long> idList) {

        String idParams = String.join(",", idList.stream().map(String::valueOf).toList());
        String namePredmParams = String.join("','", namePredmList);
        String sqlQuery = "SELECT COD_ARTIC,ID_SCLAD,MIN_TVRZAP,MAX_TVRZAP,TIP_TOVR,BALL5 FROM SCL_ARTC WHERE COD_ARTIC IN ('" + namePredmParams + "') AND ID_SCLAD IN (" + idParams + ");";
        //TODO maximum length of query?
        try {
            return jdbcTemplate.query(sqlQuery, new StockParamMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<SclArtc> getGoodsByNumDoc(long numDoc) {
        try {
            return jdbcTemplate.query("SELECT SCL_ARTC.COD_ARTIC, SCL_ARTC.NAME_ARTIC, SCL_ARTC.CENA_VALT, SCL_ARTC.COD_VALT, SCL_ARTC.KON_KOLCH, SCL_ARTC.REZ_KOLCH, SCL_ARTC.EDIN_IZMER,\n" +
                            "       SCL_ARTC.EDN_V_UPAK, SCL_ARTC.DOP2_ARTIC, SCL_ARTC.DOP3_ARTIC, SCL_ARTC.MIN_TVRZAP, SCL_ARTC.MAX_TVRZAP, SCL_ARTC.ID_SCLAD, SCL_ARTC.BALL1,\n" +
                            "       SCL_ARTC.BALL2, SCL_ARTC.BALL4, SCL_ARTC.BALL5, SCL_ARTC.TIP_TOVR\n" +
                            "FROM SCL_MOVE INNER JOIN SCL_ARTC ON (SCL_MOVE.ID_SCLAD = SCL_ARTC.ID_SCLAD) AND (SCL_MOVE.NAME_PREDM = SCL_ARTC.COD_ARTIC)\n" +
                            "WHERE (((SCL_MOVE.UNICUM_NUM)=?));",
                    new SclArtcMapper(), numDoc);
        } catch (DataAccessException e) {
            return null;
        }
    }
}

