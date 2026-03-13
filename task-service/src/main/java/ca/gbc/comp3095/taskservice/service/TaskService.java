package ca.gbc.comp3095.taskservice.service;

import ca.gbc.comp3095.taskservice.dto.TaskRequestDTO;
import ca.gbc.comp3095.taskservice.dto.TaskResponseDTO;

import java.util.List;

public interface TaskService {

    TaskResponseDTO createTask(TaskRequestDTO request);

    TaskResponseDTO getTaskByTaskId(Long taskId);

    List<TaskResponseDTO> getAllTasks();

    TaskResponseDTO updateTask(Long taskId, TaskRequestDTO request);

    void deleteTask(Long taskId);

    List<TaskResponseDTO> getTasksByCourse(Long courseId);

    List<TaskResponseDTO> getTasksByCourseAndCompletion(Long courseId, boolean completed);
}
