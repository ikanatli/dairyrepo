package com.idk.diary.config.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/*
   Swagger URL:
   if springFox  -> http://localhost:8080/swagger-ui/   or http://localhost:8080/swagger-ui/index.html
   if OpenApi(v3f custom -> check application properties for springdoc: api-docs: path:
        -> http://localhost:8080/diarySwagger
 */
@Configuration("DiarySwaggerDocumentationConfig")
public class SwaggerConfig implements WebMvcConfigurer {


    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("diary")
                .pathsToMatch("/**")
                .build();


//        return new Docket(DocumentationType.SWAGGER_2)
//                .select() // provides a way to control the endpoints exposed by Swagger.
//                .apis(RequestHandlerSelectors.basePackage("com.idk.diary"))
//                .build()
//                .ignoredParameterTypes(Throwable.class, StackTraceElement.class)
//                .directModelSubstitute(OffsetDateTime.class, java.util.Date.class)
//                .apiInfo(apiInfo());
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Diary App API")
                        .description("This SwaggerUI environment generated for the Diary Application Service.")
                        .version("0.0.1")
                        .contact(new Contact().name("ilker danyal kanatli").email("ikanatli@yahoo.com"))
                        .license(new License().name("Apache 2.0").url("https://diaryapp.wiki.github.org/docs")))
                .externalDocs(new ExternalDocumentation()
                        .description("Diary Wiki Documentation")
                        .url("https://diaryapp.wiki.github.org/docs"));
    }

//    ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Diary App Service")
//                .description("This SwaggerUI environment generated for the Diary Application Service.")
//                .license("Apache License Version 2.0")
//                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
//                .version("1.0.0")
//                .contact(new Contact("ilker danyal kanatli", "", "ikanatli@yahoo.com"))
//                .build();
//    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//
//        registry.addResourceHandler("/**")
//                .addResourceLocations("classpath:/META-INF/resources/");
//
//        registry.addResourceHandler("/static/schemas/**")
//                .addResourceLocations("classpath:/static/schemas/");
//    }


}
