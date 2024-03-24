package org.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
        info = @Info(
                title = "Example Api",
                description = "For demonstration", version = "1.0.0",
                contact = @Contact(
                        name = "Volodymyr Malakhatka",
                        email = "vmalakhatka@gmail.com",
                        url = " "
                )
        )/*,
        security = @SecurityRequirement(name = "FormAuth")*/ // для установки для всех эндпоинтов
)
//@SecurityScheme(
//        name = "FormAuth",
//        type = SecuritySchemeType.APIKEY, // APIKEY используется для описания, но реальная аутентификация через форму не поддерживается Swagger'ом напрямую
//        in = SecuritySchemeIn.COOKIE, // Предполагаем использование cookie для сессии
//        paramName = "JSESSIONID" // Имя cookie, используемое для сессии в Spring Security
//)
public class OpenApiConfig { }

