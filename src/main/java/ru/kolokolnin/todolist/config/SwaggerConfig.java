package ru.kolokolnin.todolist.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI todoListOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Todo List REST API")
                        .description("Простое RESTful API для управления списком задач")
                        .version("1.0.0"));
    }
}
