package org.example.proect_lavka.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record DatabaseProperties(
        @Value("${spring.datasource.url}") String url,
        @Value("${spring.datasource.username}") String username,
        @Value("${spring.datasource.password}") String password,
        @Value("${spring.datasource.driver-class-name}") String driverClassName
) { }