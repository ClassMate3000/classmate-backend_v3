package ca.gbc.comp3095.taskservice.controller;

import ca.gbc.comp3095.taskservice.dto.TaskRequestDTO;
import ca.gbc.comp3095.taskservice.dto.TaskResponseDTO;
import ca.gbc.comp3095.taskservice.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public TaskResponseDTO createTask(@Valid @RequestBody TaskRequestDTO request) {
        return service.createTask(request);
    }

    @GetMapping("/{taskId}")
    public TaskResponseDTO getTaskByTaskId(@PathVariable Long taskId) {
        return service.getTaskByTaskId(taskId);
    }

    @GetMapping
    public List<TaskResponseDTO> getAllTasks() {
        return service.getAllTasks();
    }

    // tasks for a course
    @GetMapping("/course/{courseId}")
    public List<TaskResponseDTO> getTasksByCourse(@PathVariable Long courseId) {
        return service.getTasksByCourse(courseId);
    }

    // tasks for a course by completion
    @GetMapping("/course/{courseId}/completed/{status}")
    public List<TaskResponseDTO> getTasksByCourseAndCompletion(@PathVariable Long courseId, @PathVariable boolean status) {
        return service.getTasksByCourseAndCompletion(courseId, status);
    }

    @PutMapping("/{taskId}")
    public TaskResponseDTO updateTask(@PathVariable Long taskId,
                                     @Valid @RequestBody TaskRequestDTO request) {
        return service.updateTask(taskId, request);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        service.deleteTask(taskId);
    }
}
