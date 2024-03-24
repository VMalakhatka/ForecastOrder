package org.example.repository.forecast;

import org.example.entity.forecast.StockTipSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTipSaleRepository extends JpaRepository<StockTipSale,Long> {
}
