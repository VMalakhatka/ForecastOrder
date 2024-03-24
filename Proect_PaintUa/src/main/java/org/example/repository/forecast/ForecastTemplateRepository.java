package org.example.repository.forecast;

import org.example.entity.forecast.ForecastTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastTemplateRepository extends JpaRepository<ForecastTemplate,Long> {
}
