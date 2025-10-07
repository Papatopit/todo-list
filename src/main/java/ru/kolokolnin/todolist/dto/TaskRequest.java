package ru.kolokolnin.todolist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание или обновление задачи")
public record TaskRequest(
        @Schema(
                description = "Название задачи",
                example = "Изучить Spring Boot",
                requiredMode = Schema.RequiredMode.REQUIRED,
                maxLength = 500
        )
        @NotBlank(message = "Title is required")
        @Size(max = 500, message = "Title must not exceed 500 characters")
        String title,

        @Schema(
                description = "Описание задачи",
                example = "Изучить основы Spring Boot и создание REST API",
                maxLength = 2000
        )
        @Size(max = 2000, message = "Description must not exceed 2000 characters")
        String description,

        @Schema(
                description = "Статус выполнения задачи",
                example = "false"
        )
        @JsonProperty("completed")
        Boolean completed
) {
}
