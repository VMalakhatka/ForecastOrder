package org.example.proect_lavka.dao.mapper;

import org.example.proect_lavka.entity.SclMove;
import org.springframework.jdbc.core.RowMapper;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SclMoveMapper implements RowMapper<SclMove> {

    @Override
    public SclMove mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new SclMove(
                rs.getString("NAME_PREDM"),
                rs.getDouble("UNICUM_NUM"),
                rs.getLong("NUMDOCM_PR"),
                rs.getString("NUMDCM_DOP"),
                rs.getString("ORG_PREDM"),
                rs.getTimestamp("DATE_PREDM"),
                rs.getDouble("KOLC_PREDM"),
                rs.getString("TYPDOCM_PR"),
                rs.getString("VID_DOC"),
                rs.getInt("ID_SCLAD")
        );
    }
}
