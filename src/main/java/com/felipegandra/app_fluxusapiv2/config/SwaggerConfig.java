package com.felipegandra.app_fluxusapiv2.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .info(new Info()
                        .title("API Fluxus")
                        .version("2.0")
                        )
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }

    @Bean
    public GroupedOpenApi userLoginApi() {
        return GroupedOpenApi.builder()
                .group("Access")
                .pathsToMatch("/v2/users/login", "/v2/users/register")
                .build();
    }

    @Bean
    public GroupedOpenApi otherApis() {
        return GroupedOpenApi.builder()
                .group("Endpoints")
                .pathsToExclude("/v2/users/login", "/v2/users/register")
                .build();
    }
}
