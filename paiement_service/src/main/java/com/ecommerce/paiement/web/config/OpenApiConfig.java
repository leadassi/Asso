package com.ecommerce.paiement.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API du microservice de gestion des paiements.")
                        .version("1.0")
                        .description("Documentation pour l'API REST de paiement des commandes.")
                        .contact(new Contact()
                                .name("Asso API")
                                .email("sonianj252@gmail.com")
                                .url("https://exemple.com")));
    }
}
