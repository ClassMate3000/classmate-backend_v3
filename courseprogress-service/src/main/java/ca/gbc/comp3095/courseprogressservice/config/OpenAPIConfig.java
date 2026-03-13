package ca.gbc.comp3095.courseprogressservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CourseProgress Service API")
                        .version("v1")
                        .description("REST API for CourseProgress Service")
                )
                .externalDocs(new ExternalDocumentation()
                        .description("ClassMate Backend Documentation")
                        .url("https://github.com/JamesJones-compsci/classmate-backend_winter_break25"));
    }
}
