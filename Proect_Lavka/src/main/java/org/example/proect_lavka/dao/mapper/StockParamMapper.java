package org.example.proect_lavka.dao.mapper;

import org.example.proect_lavka.dto.RestDtoOut;
import org.example.proect_lavka.dto.StockParamDtoOut;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockParamMapper implements RowMapper<StockParamDtoOut> {
    @Override
    public StockParamDtoOut mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StockParamDtoOut(rs.getString("COD_ARTIC"),
                rs.getLong("ID_SCLAD")
                ,
                rs.getDouble("MIN_TVRZAP"),
                rs.getDouble("MAX_TVRZAP"),
                Integer.parseInt(rs.getString("TIP_TOVR")),
               (int)rs.getDouble("BALL5")
              );
    }
}
