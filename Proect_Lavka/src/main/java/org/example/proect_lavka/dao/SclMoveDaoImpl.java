package org.example.proect_lavka.dao;

import org.example.proect_lavka.dao.mapper.SclMoveMapper;
import org.example.proect_lavka.entity.SclMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
@Component
public class SclMoveDaoImpl implements SclMoveDao{

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public SclMoveDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SclMove> getMoveByGoodsAndData(String NamePredm, int id,String start, String end) {

                return jdbcTemplate.query("SELECT * FROM SCL_MOVE WHERE NAME_PREDM=? AND ID_SCLAD=? AND DATE_PREDM>=? AND DATE_PREDM<=?;",
                new SclMoveMapper(),NamePredm,id,start,end);

    }

    @Override
    public List<SclMove> getMoveByListOfGoodsAndData(List<String> namePredmList, List<Long> idList, String start, String end) {
        //String idParams = String.join(",", Collections.nCopies(idList.size(), "?"));
        String idParams = String.join(",", idList.stream().map(String::valueOf).toList());

       // String namePredmParams = String.join(",", Collections.nCopies(namePredmList.size(), "?"));
        String namePredmParams = String.join("','", namePredmList);

        String sqlQuery = "SELECT * FROM SCL_MOVE WHERE NAME_PREDM IN ('" + namePredmParams + "') AND ID_SCLAD IN (" + idParams + ") AND DATE_PREDM>=? AND DATE_PREDM<=?;";
//TODO maximum length of query?
        // String sqlQuery = "SELECT * FROM SCL_MOVE WHERE NAME_PREDM IN (" + namePredmParams + ") AND ID_SCLAD=? AND DATE_PREDM>=? AND DATE_PREDM<=?;";

        //List<SclMove> sclMoveList= jdbcTemplate.query(sqlQuery, new SclMoveMapper(), namePredmList.toArray(), idList.toArray(), start, end);
        List<SclMove> sclMoveList= jdbcTemplate.query(sqlQuery, new SclMoveMapper(), start, end);
        return sclMoveList;
        //return null;
    }
}
