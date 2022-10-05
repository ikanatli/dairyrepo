package com.idk.diary.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/*
   Swagger URL:  http://localhost:8080/swagger-ui/
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select() // provides a way to control the endpoints exposed by Swagger.
                .apis(RequestHandlerSelectors.any()) // Using any() make documentation for our entire API available through Swagger.
                .paths(PathSelectors.any()) // Using any() make documentation for our entire API available through Swagger.
                .build();
    }

}
