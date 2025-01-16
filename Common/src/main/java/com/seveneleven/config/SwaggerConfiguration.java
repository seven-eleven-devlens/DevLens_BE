package com.seveneleven.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.core.jackson.TypeNameResolver;
import jakarta.annotation.PostConstruct;
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
}
