package com.projects.hrproject.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "HR Management API REST",
        version = "1.0",
        description = "API REST liée à la web app HR Management permettant la gestion des employés"
    )
)
public class OpenApiConfig {

}
