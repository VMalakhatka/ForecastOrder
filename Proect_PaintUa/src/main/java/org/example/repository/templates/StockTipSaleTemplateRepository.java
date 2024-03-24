package org.example.repository.templates;

import org.example.entity.templates.StockTipSaleTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTipSaleTemplateRepository extends JpaRepository<StockTipSaleTemplate,Long> {
}
