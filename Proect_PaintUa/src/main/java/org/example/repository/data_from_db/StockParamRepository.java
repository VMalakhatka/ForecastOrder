package org.example.repository.data_from_db;

import org.example.entity.data_from_db.StockParam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockParamRepository extends JpaRepository<StockParam,Long> {
}
