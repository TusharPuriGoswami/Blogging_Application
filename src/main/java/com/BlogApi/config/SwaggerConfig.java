package com.BlogApi.config;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    // Create an ApiKey for JWT
    private ApiKey createApiKey() {
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    // Configure security contexts
    private SecurityContext createSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    // Define security references
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        return Collections.singletonList(
                new SecurityReference("JWT", new AuthorizationScope[]{authorizationScope})
        );
    }

    // Create API Info
    private ApiInfo createApiInfo() {
        return new ApiInfo(
                "Blogging Application API Documentation",
                "This project is a backend API for a blogging platform.",
                "1.0",
                "Terms of Service",
                new Contact("Your Name", "https://yourwebsite.com", "your.email@example.com"),
                "API License",
                "https://yourlicenseurl.com",
                Collections.emptyList()
        );
    }

    @Bean
    public Docket configureSwagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(createApiInfo())
                .securityContexts(Collections.singletonList(createSecurityContext()))
                .securitySchemes(Collections.singletonList(createApiKey()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.BlogApi")) // Limit scanning to specific package
                .paths(PathSelectors.any())
                .build();
    }
}


//
//import java.util.Arrays;
//
//import java.util.Collections;
//import java.util.List;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//
//
//
//@Configuration
//public class SwaggerConfig {
//
//    public static final String AUTHORIZATION_HEADER = "Authorization";
//
//    private ApiKey apiKey() {
//        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
//    }
//
//    private List<SecurityContext> securityContexts() {
//        return Arrays.asList(SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .build());
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//    }
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .securityContexts(securityContexts())
//                .securitySchemes(Arrays.asList(apiKey()))
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfo(
//                "Blogging Application: Backend Course",
//                "This is developed by Tushar Goswami",
//                "1.0",
//                "Terms of Service",
//                new Contact("Tushar Goswami", "https://codewithtushar.com", "tusharpuri155@gmail.com"),
//                "License of APIs",
//                "API License URL",
//                Collections.emptyList());
//    }
//}
