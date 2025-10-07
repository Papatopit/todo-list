package ru.kolokolnin.todolist.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolokolnin.todolist.dto.TaskRequest;
import ru.kolokolnin.todolist.dto.TaskResponse;
import ru.kolokolnin.todolist.entity.TaskEntity;
import ru.kolokolnin.todolist.exception.TaskNotFoundException;
import ru.kolokolnin.todolist.mapper.TaskMapper;
import ru.kolokolnin.todolist.repo.TaskRepository;
import ru.kolokolnin.todolist.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest taskRequest) {
        log.info("Creating new task with title: {}", taskRequest.title());

        TaskEntity taskEntity = taskMapper.toEntity(taskRequest);
        LocalDateTime now = LocalDateTime.now();
        taskEntity.setCreatedAt(now);
        taskEntity.setUpdatedAt(now);

        TaskEntity savedEntity = taskRepository.save(taskEntity);

        log.debug("Task created successfully with ID: {}", savedEntity.getId());
        return taskMapper.toResponse(savedEntity);
    }

    @Override
    public List<TaskResponse> getAllTasks() {
        log.info("Retrieving all tasks");
        List<TaskEntity> tasks = taskRepository.findAll();
        log.debug("Found {} tasks", tasks.size());
        return taskMapper.toResponseList(tasks);
    }

    @Override
    public TaskResponse getTaskById(UUID id) {
        log.info("Retrieving task by ID: {}", id);
        TaskEntity taskEntity = findTaskById(id);
        return taskMapper.toResponse(taskEntity);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(UUID id, TaskRequest taskRequest) {
        log.info("Updating task with ID: {}", id);

        TaskEntity taskEntity = findTaskById(id);
        taskMapper.updateEntityFromRequest(taskRequest, taskEntity);

        if (taskRequest.completed() != null) {
            taskEntity.setCompleted(taskRequest.completed());
        }

        taskEntity.setUpdatedAt(LocalDateTime.now());

        TaskEntity updatedEntity = taskRepository.save(taskEntity);
        log.debug("Task updated successfully with ID: {}", id);

        return taskMapper.toResponse(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteTask(UUID id) {
        log.info("Deleting task with ID: {}", id);

        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }

        taskRepository.deleteById(id);
        log.debug("Task deleted successfully with ID: {}", id);
    }

    private TaskEntity findTaskById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
