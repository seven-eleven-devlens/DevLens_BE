package com.seveneleven.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.jackson.TypeNameResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    private final ObjectMapper objectMapper;

    public SwaggerConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void initialize() {
        TypeNameResolver innerClassAwareTypeNameResolver = new TypeNameResolver() {
            @Override
            public String getNameOfClass(Class<?> cls) {
                /*
                    cls.getName() -> com.seveneleven.project.dto.GetProjectList$Request
                    subString(lastIndexOf(".") + 1) -> GetProjectList$Request
                    replace("$", ".") -> GetProjectList.Request
                 */
                return cls.getName()
                        .substring(cls.getName().lastIndexOf(".") + 1)
                        .replace("$", "");
            }
        };

        ModelConverters.getInstance()
                .addConverter(new ModelResolver(objectMapper, innerClassAwareTypeNameResolver));
    }

    @Bean
    public OpenAPI accessApi() {
        // Access Token (Authorization 헤더)
        SecurityScheme accessTokenScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // Refresh Token (X-Refresh-Token 헤더)
        SecurityScheme refreshTokenScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-Refresh-Token");

        // Security 요구사항 설정 (Bearer Token과 Refresh Token 추가)
        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Token")
                .addList("Refresh Token");

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("Bearer Token", accessTokenScheme)
                        .addSecuritySchemes("Refresh Token", refreshTokenScheme))
                .addSecurityItem(securityRequirement);
    }
}
