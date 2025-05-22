package com.petshome.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
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

import java.util.Arrays;
import java.util.Collections;

/**
 * Swagger API文档配置
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.petshome.api.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Arrays.asList(
                    new ApiKey("Authorization", "Authorization", "header")
                ))
                .securityContexts(Collections.singletonList(
                    SecurityContext.builder()
                        .securityReferences(
                            Arrays.asList(
                                new SecurityReference("Authorization",
                                    new AuthorizationScope[] {
                                        new AuthorizationScope("global", "accessEverything")
                                    }
                                )
                            )
                        )
                        .operationSelector(operationContext -> {
                            String pattern = operationContext.requestMappingPattern();
                            // 排除不需要认证的接口
                            return !pattern.contains("/auth") &&
                                   !pattern.contains("/user/login") &&
                                   !pattern.contains("/user/register") &&
                                   !pattern.contains("/admin/login") &&
                                   !pattern.contains("/swagger-ui") &&
                                   !pattern.contains("/v3/api-docs");
                        })
                        .build()
                )); // 添加JWT认证支持
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("萌宠之家API文档")
                .description("萌宠之家后端API接口文档")
                .contact(new Contact("PetsHome Team", "https://www.petshome.com", "contact@petshome.com"))
                .version("1.0.0")
                .build();
    }
}