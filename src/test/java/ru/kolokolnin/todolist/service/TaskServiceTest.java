package ru.kolokolnin.todolist.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kolokolnin.todolist.dto.TaskRequest;
import ru.kolokolnin.todolist.dto.TaskResponse;
import ru.kolokolnin.todolist.entity.TaskEntity;
import ru.kolokolnin.todolist.exception.TaskNotFoundException;
import ru.kolokolnin.todolist.mapper.TaskMapper;
import ru.kolokolnin.todolist.repo.TaskRepository;
import ru.kolokolnin.todolist.service.impl.TaskServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void createTask_ShouldReturnTaskResponse() {
        TaskRequest request = new TaskRequest("Test Task", "Test Description", false);
        TaskEntity entity = TaskEntity.builder()
                .id(UUID.randomUUID())
                .title("Test Task")
                .description("Test Description")
                .completed(false)
                .build();
        TaskResponse response = new TaskResponse(
                entity.getId(), "Test Task", "Test Description", false, null, null);

        when(taskMapper.toEntity(request)).thenReturn(entity);
        when(taskRepository.save(entity)).thenReturn(entity);
        when(taskMapper.toResponse(entity)).thenReturn(response);

        TaskResponse result = taskService.createTask(request);

        assertNotNull(result);
        assertEquals("Test Task", result.title());
        verify(taskRepository, times(1)).save(entity);
    }

    @Test
    void getTaskById_WhenTaskExists_ShouldReturnTaskResponse() {
        UUID taskId = UUID.randomUUID();
        TaskEntity entity = TaskEntity.builder().id(taskId).build();
        TaskResponse response = new TaskResponse(taskId, "Test", null, false, null, null);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(entity));
        when(taskMapper.toResponse(entity)).thenReturn(response);

        TaskResponse result = taskService.getTaskById(taskId);

        assertNotNull(result);
        assertEquals(taskId, result.id());
    }

    @Test
    void getTaskById_WhenTaskNotExists_ShouldThrowException() {
        UUID taskId = UUID.randomUUID();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(taskId));
    }

    @Test
    void getAllTasks_ShouldReturnListOfTasks() {
        TaskEntity entity1 = TaskEntity.builder().id(UUID.randomUUID()).build();
        TaskEntity entity2 = TaskEntity.builder().id(UUID.randomUUID()).build();
        List<TaskEntity> entities = List.of(entity1, entity2);

        when(taskRepository.findAll()).thenReturn(entities);
        when(taskMapper.toResponseList(entities)).thenReturn(List.of(
                new TaskResponse(entity1.getId(), "Task 1", null, false, null, null),
                new TaskResponse(entity2.getId(), "Task 2", null, false, null, null)
        ));

        List<TaskResponse> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }
}
