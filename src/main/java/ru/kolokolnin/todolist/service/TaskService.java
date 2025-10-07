package ru.kolokolnin.todolist.service;

import ru.kolokolnin.todolist.dto.TaskRequest;
import ru.kolokolnin.todolist.dto.TaskResponse;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    TaskResponse createTask(TaskRequest taskRequest);
    List<TaskResponse> getAllTasks();
    TaskResponse getTaskById(UUID id);
    TaskResponse updateTask(UUID id, TaskRequest taskRequest);
    void deleteTask(UUID id);
}