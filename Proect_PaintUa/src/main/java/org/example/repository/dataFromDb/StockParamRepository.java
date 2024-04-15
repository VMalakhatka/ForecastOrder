package org.example.repository.dataFromDb;

import org.example.entity.dataFromDb.StockParam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockParamRepository extends JpaRepository<StockParam,Long> {
}
