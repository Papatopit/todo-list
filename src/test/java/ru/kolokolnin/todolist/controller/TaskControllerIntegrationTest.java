package ru.kolokolnin.todolist.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.kolokolnin.todolist.dto.TaskRequest;
import ru.kolokolnin.todolist.entity.TaskEntity;
import ru.kolokolnin.todolist.repo.TaskRepository;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    private UUID existingTaskId;

    @BeforeEach
    void setUp() {
        taskRepository.deleteAll();

        TaskEntity task = TaskEntity.builder()
                .title("Existing Task")
                .description("Existing Description")
                .completed(false)
                .build();

        TaskEntity savedTask = taskRepository.save(task);
        existingTaskId = savedTask.getId();
    }

    @Test
    void createTask_IntegrationTest() throws Exception {
        // Given
        TaskRequest request = new TaskRequest("Integration Task", "Integration Description", false);

        // When & Then
        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value("Integration Task"))
                .andExpect(jsonPath("$.description").value("Integration Description"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.updatedAt").isNotEmpty());
    }

    @Test
    void getTaskById_IntegrationTest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/tasks/{id}", existingTaskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingTaskId.toString()))
                .andExpect(jsonPath("$.title").value("Existing Task"))
                .andExpect(jsonPath("$.description").value("Existing Description"));
    }

    @Test
    void getTaskById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        // Given
        UUID nonExistentId = UUID.fromString("11111111-1111-1111-1111-111111111111");

        // When & Then
        mockMvc.perform(get("/api/v1/tasks/{id}", nonExistentId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("TASK_NOT_FOUND"));
    }

    @Test
    void updateTask_IntegrationTest() throws Exception {
        // Given
        TaskRequest request = new TaskRequest("Updated Task", "Updated Description", true);

        // When & Then
        mockMvc.perform(put("/api/v1/tasks/{id}", existingTaskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void deleteTask_IntegrationTest() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/v1/tasks/{id}", existingTaskId))
                .andExpect(status().isNoContent());

        // Verify task is actually deleted
        mockMvc.perform(get("/api/v1/tasks/{id}", existingTaskId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllTasks_IntegrationTest() throws Exception {
        // Given
        TaskEntity task2 = TaskEntity.builder()
                .title("Second Task")
                .description("Second Description")
                .completed(true)
                .build();
        taskRepository.save(task2);

        // When & Then
        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Existing Task"))
                .andExpect(jsonPath("$[1].title").value("Second Task"));
    }
}
