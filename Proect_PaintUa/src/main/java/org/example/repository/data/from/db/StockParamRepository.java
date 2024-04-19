package org.example.repository.data.from.db;

import org.example.entity.data.from.db.StockParam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockParamRepository extends JpaRepository<StockParam,Long> {
}
