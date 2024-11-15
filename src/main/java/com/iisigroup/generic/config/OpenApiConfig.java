package com.iisigroup.generic.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        security = {@SecurityRequirement(name = "security_auth"), @SecurityRequirement(name = "jwt_auth")}, // Global security requirement
        info = @Info(
                title = "Ocarbon Generic API",
                description = "Generic API",
                version = "v1"))
@SecurityScheme(
        name = "security_auth",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(authorizationCode =
        @OAuthFlow(authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
                tokenUrl = "${springdoc.oAuthFlow.tokenUrl}")))
@SecurityScheme(
        name = "jwt_auth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")
@Configuration
public class OpenApiConfig {


    private static final String[] esgPath = {
            "/auth/**"
    };
    private static final String[] oc64Path = {
            "/oc64/**"
    };
    private static final String[] oc67Path = {
            "/oc67/**"
    };


    @Bean
    public GroupedOpenApi esgApi() {
        return GroupedOpenApi.builder()
                .group("esg")
                .pathsToMatch(esgPath)
                .build();
    }

    @Bean
    public GroupedOpenApi oc64Api() {
        return GroupedOpenApi.builder()
                .group("oc64")
                .pathsToMatch(oc64Path)
                .build();
    }

    @Bean
    public GroupedOpenApi oc67Api() {
        return GroupedOpenApi.builder()
                .group("oc67")
                .pathsToMatch(oc67Path)
                .build();
    }


}
