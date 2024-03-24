package org.example.repository.templates;

import org.example.entity.templates.SetStockTtTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetStockTtTemplateRepository extends JpaRepository<SetStockTtTemplate, Long> {
}
