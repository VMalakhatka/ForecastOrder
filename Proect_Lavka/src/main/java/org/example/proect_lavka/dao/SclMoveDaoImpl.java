package org.example.proect_lavka.dao;

import org.example.proect_lavka.dao.mapper.SclMoveMapper;
import org.example.proect_lavka.entity.SclMove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
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

                return jdbcTemplate.query("SELECT * FROM SCL_MOVE WHERE NAME_PREDM=? AND ID_SCLAD=? AND DATE_PREDM>=?AND DATE_PREDM<=?;",
                new SclMoveMapper(),NamePredm,id,start,end);

    }
}
