package ca.gbc.comp3095.taskservice.controller;

import ca.gbc.comp3095.taskservice.dto.TaskRequestDTO;
import ca.gbc.comp3095.taskservice.dto.TaskResponseDTO;
import ca.gbc.comp3095.taskservice.model.TaskType;
import ca.gbc.comp3095.taskservice.service.TaskService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService service;

    @Autowired
    private ObjectMapper objectMapper;

    // CREATE
    @Test
    void shouldCreateTask() throws Exception {

        TaskRequestDTO request = new TaskRequestDTO();
        request.setCourseId(101L);
        request.setTitle("Test Task");
        request.setType(TaskType.ASSIGNMENT);
        request.setDueDate(LocalDateTime.now());
        request.setCompleted(false);
        request.setBonus(false);
        request.setPriority(false);
        request.setPriorityThresholdDays(0);
        request.setManualPriorityOverride(false);
        request.setWeight(0.0);
        request.setScorePercent(0.0);

        TaskResponseDTO response = new TaskResponseDTO(
                1L, 101L, "Test Task", TaskType.ASSIGNMENT,
                LocalDateTime.now(),
                false, false, false,
                0, false,
                0.0, 0.0
        );

        when(service.createTask(any(TaskRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(1L));
    }



    // GET BY ID
    @Test
    void shouldGetTaskById() throws Exception {

        TaskResponseDTO response = new TaskResponseDTO(
                1L, 101L, "Test Task", TaskType.ASSIGNMENT,
                LocalDateTime.now(),
                false, false, false,
                0, false,
                0.0, 0.0
        );

        when(service.getTaskByTaskId(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(1L));
    }

    // GET ALL
    @Test
    void shouldGetAllTasks() throws Exception {

        TaskResponseDTO taskResponse = new TaskResponseDTO(
                1L, 101L, "Task 1", TaskType.ASSIGNMENT,
                LocalDateTime.now(),
                false, false, false,
                0, false,
                0.0, 0.0
        );

        when(service.getAllTasks()).thenReturn(List.of(taskResponse));

        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    // DELETE
    @Test
    void shouldDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/v1/tasks/1"))
                .andExpect(status().isOk());
    }

}