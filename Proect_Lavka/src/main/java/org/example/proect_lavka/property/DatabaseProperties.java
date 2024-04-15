package org.example.proect_lavka.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//@Component
//public record DatabaseProperties(
//        @Value("${spring.datasource.url}") String url,
//        @Value("${spring.datasource.username}") String username,
//        @Value("${spring.datasource.password}") String password,
//        @Value("${spring.datasource.driver-class-name}") String driverClassName
//) { }


@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DatabaseProperties{
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}