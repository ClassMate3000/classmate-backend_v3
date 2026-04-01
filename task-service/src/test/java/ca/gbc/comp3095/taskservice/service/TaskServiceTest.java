package ca.gbc.comp3095.taskservice.service;

import ca.gbc.comp3095.taskservice.dto.TaskRequestDTO;
import ca.gbc.comp3095.taskservice.dto.TaskResponseDTO;
import ca.gbc.comp3095.taskservice.exception.TaskNotFoundException;
import ca.gbc.comp3095.taskservice.model.Task;
import ca.gbc.comp3095.taskservice.model.TaskType;
import ca.gbc.comp3095.taskservice.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private SequenceGeneratorService sequenceGenerator;
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        sequenceGenerator = mock(SequenceGeneratorService.class);
        taskService = new TaskServiceImpl(taskRepository, sequenceGenerator);
    }

    // Helper to create a TaskRequestDTO
    private TaskRequestDTO sampleRequest() {
        TaskRequestDTO request = new TaskRequestDTO();
        request.setCourseId(100L);
        request.setTitle("Test Task");
        request.setType(TaskType.ASSIGNMENT);
        request.setDueDate(LocalDateTime.now().plusDays(3));
        request.setCompleted(false);
        request.setBonus(false);
        request.setPriority(false);
        request.setPriorityThresholdDays(0);
        request.setManualPriorityOverride(false);
        request.setWeight(0.0);
        request.setScorePercent(0.0);
        return request;
    }

    // CREATE
    @Test
    void shouldCreateTask() {
        when(sequenceGenerator.getNextSequence("task_sequence")).thenReturn(1L);
        Task savedTask = new Task();
        savedTask.setTaskId(1L);
        savedTask.setCourseId(100L);
        savedTask.setTitle("Test Task");
        savedTask.setType(TaskType.ASSIGNMENT);
        savedTask.setDueDate(LocalDateTime.now().plusDays(3));

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponseDTO response = taskService.createTask(sampleRequest());
        assertThat(response.getTaskId()).isEqualTo(1L);
        assertThat(response.getTitle()).isEqualTo("Test Task");

        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(captor.capture());
        assertThat(captor.getValue().getTaskId()).isEqualTo(1L);
    }

    // GET BY ID
    @Test
    void shouldGetTaskById() {
        Task task = new Task();
        task.setTaskId(2L);
        task.setTitle("Sample");
        when(taskRepository.findByTaskId(2L)).thenReturn(Optional.of(task));

        TaskResponseDTO response = taskService.getTaskByTaskId(2L);
        assertThat(response.getTaskId()).isEqualTo(2L);
        assertThat(response.getTitle()).isEqualTo("Sample");
    }

    @Test
    void shouldThrowWhenTaskNotFound() {
        when(taskRepository.findByTaskId(999L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskByTaskId(999L));
    }

    // GET ALL
    @Test
    void shouldGetAllTasks() {
        Task task1 = new Task();
        task1.setTaskId(1L);
        Task task2 = new Task();
        task2.setTaskId(2L);

        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));
        List<TaskResponseDTO> result = taskService.getAllTasks();
        assertThat(result).hasSize(2);
    }

    // UPDATE
    @Test
    void shouldUpdateTask() {
        Task existing = new Task();
        existing.setTaskId(3L);
        existing.setTitle("Old Title");

        when(taskRepository.findByTaskId(3L)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenAnswer(i -> i.getArgument(0));

        TaskRequestDTO updateRequest = sampleRequest();
        updateRequest.setTitle("Updated Title");

        TaskResponseDTO updated = taskService.updateTask(3L, updateRequest);
        assertThat(updated.getTitle()).isEqualTo("Updated Title");
    }

    @Test
    void shouldThrowWhenUpdateNonexistentTask() {
        when(taskRepository.findByTaskId(999L)).thenReturn(Optional.empty());
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(999L, sampleRequest()));
    }

    // DELETE
    @Test
    void shouldDeleteTask() {
        when(taskRepository.existsByTaskId(4L)).thenReturn(true);
        taskService.deleteTask(4L);
        verify(taskRepository).deleteByTaskId(4L);
    }

    @Test
    void shouldThrowWhenDeleteNonexistentTask() {
        when(taskRepository.existsByTaskId(999L)).thenReturn(false);
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(999L));
    }

    // GET BY COURSE
    @Test
    void shouldGetTasksByCourse() {
        Task t1 = new Task();
        t1.setCourseId(101L);
        Task t2 = new Task();
        t2.setCourseId(101L);
        when(taskRepository.findByCourseId(101L)).thenReturn(List.of(t1, t2));

        List<TaskResponseDTO> tasks = taskService.getTasksByCourse(101L);
        assertThat(tasks).hasSize(2);
    }

    // GET BY COURSE AND COMPLETION
    @Test
    void shouldGetTasksByCourseAndCompletion() {
        Task t1 = new Task();
        t1.setCourseId(201L);
        t1.setCompleted(true);

        when(taskRepository.findByCourseIdAndIsCompleted(201L, true)).thenReturn(List.of(t1));

        List<TaskResponseDTO> tasks = taskService.getTasksByCourseAndCompletion(201L, true);
        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).isCompleted()).isTrue();
    }
}