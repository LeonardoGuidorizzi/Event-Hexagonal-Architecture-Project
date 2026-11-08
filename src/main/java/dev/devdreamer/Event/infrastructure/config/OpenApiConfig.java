package dev.devdreamer.Event.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI eventOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Event API")
                .version("v1")
                .description("API for creating, publishing, and canceling events."));
    }
}
