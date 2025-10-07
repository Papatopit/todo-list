package ru.kolokolnin.todolist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(description = "Ответ об ошибке")
public record ErrorResponse(
        @Schema(description = "Сообщение об ошибке", example = "Task not found")
        String message,

        @Schema(description = "Код ошибки", example = "TASK_NOT_FOUND")
        String errorCode,

        @Schema(description = "Время возникновения ошибки", example = "2024-01-15T10:30:00")
        LocalDateTime timestamp
) {
}