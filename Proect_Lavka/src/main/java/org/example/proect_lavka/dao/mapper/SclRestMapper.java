package org.example.proect_lavka.dao.mapper;

import org.example.proect_lavka.dto.RestDtoOut;
import org.example.proect_lavka.entity.SclMove;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SclRestMapper implements RowMapper<RestDtoOut> {

    @Override
    public RestDtoOut mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RestDtoOut(
                rs.getString("COD_ARTIC").replaceAll("\\s+$", ""),
                rs.getLong("ID_SCLAD"),
                rs.getDouble("REZ_KOLCH"),
                rs.getDouble("KON_KOLCH")
        );
    }
}
