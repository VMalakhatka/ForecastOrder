package org.example.repository.forecast;

import org.example.entity.forecast.SetStockTT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetStockTTRepository extends JpaRepository<SetStockTT,Long> {
}
