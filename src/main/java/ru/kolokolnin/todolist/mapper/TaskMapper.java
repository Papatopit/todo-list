package ru.kolokolnin.todolist.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import ru.kolokolnin.todolist.dto.TaskRequest;
import ru.kolokolnin.todolist.dto.TaskResponse;
import ru.kolokolnin.todolist.entity.TaskEntity;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    TaskEntity toEntity(TaskRequest taskRequest);

    TaskResponse toResponse(TaskEntity taskEntity);

    List<TaskResponse> toResponseList(List<TaskEntity> taskEntities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    void updateEntityFromRequest(TaskRequest taskRequest, @MappingTarget TaskEntity taskEntity);

    @Named("toLocalDateTime")
    default LocalDateTime toLocalDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime : LocalDateTime.now();
    }
}
