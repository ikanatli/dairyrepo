package com.idk.diary.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.OffsetDateTime;


/*
   Swagger URL:  http://localhost:8080/swagger-ui/
 */
@Configuration("DiarySwaggerDocumentationConfig")
public class SwaggerConfig implements WebMvcConfigurer {



    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select() // provides a way to control the endpoints exposed by Swagger.
                .apis(RequestHandlerSelectors.basePackage("com.idk.diary"))
                .build()
                .ignoredParameterTypes(Throwable.class, StackTraceElement.class)
                .directModelSubstitute(OffsetDateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Diary App Service")
                .description("This SwaggerUI environment generated for the Diary Application Service.")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .version("1.0.0")
                .contact(new Contact("ilker danyal kanatli", "", "ikanatli@yahoo.com"))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/static/schemas/**")
                .addResourceLocations("classpath:/static/schemas/");
    }



}
