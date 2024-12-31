package com.campus_connect.user_management.config;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI manageUserApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Management API")
                        .description("REST Api for User Management")
                        .version("1.0")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
        .externalDocs(new ExternalDocumentation().description("Spring Boot docs").url("https://springdoc.org"));
    }
}
