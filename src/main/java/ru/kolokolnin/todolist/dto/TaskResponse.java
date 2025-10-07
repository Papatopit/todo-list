package ru.kolokolnin.todolist.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Ответ с данными задачи")
public record TaskResponse(
        @Schema(description = "Уникальный идентификатор задачи", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
        UUID id,

        @Schema(description = "Название задачи", example = "Изучить Spring Boot")
        String title,

        @Schema(description = "Описание задачи", example = "Изучить основы Spring Boot и создание REST API")
        String description,

        @Schema(description = "Статус выполнения задачи", example = "false")
        Boolean completed,

        @Schema(description = "Дата и время создания", example = "2024-01-15T10:30:00")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdAt,

        @Schema(description = "Дата и время последнего обновления", example = "2024-01-15T10:30:00")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime updatedAt
) {
}