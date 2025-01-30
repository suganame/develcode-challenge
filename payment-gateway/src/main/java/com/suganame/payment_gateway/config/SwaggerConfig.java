package com.suganame.payment_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // Usando um filtro de pacotes para escanear vários pacotes dinamicamente
                .apis(RequestHandlerSelectors.basePackage("com.suganame.payment_gateway"))
                .paths(PathSelectors.any())
                .build();
    }
}