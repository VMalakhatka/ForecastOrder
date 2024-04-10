package org.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
        ),
        security = @SecurityRequirement(name = "BasicAuth") // для установки для всех эндпоинтов
)
@SecurityScheme(
        name = "BasicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class OpenApiConfig { }

