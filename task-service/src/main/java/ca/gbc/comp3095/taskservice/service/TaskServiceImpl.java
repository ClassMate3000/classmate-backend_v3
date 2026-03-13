package ca.gbc.comp3095.taskservice.service;

import ca.gbc.comp3095.taskservice.dto.TaskRequestDTO;
import ca.gbc.comp3095.taskservice.dto.TaskResponseDTO;
import ca.gbc.comp3095.taskservice.exception.TaskNotFoundException;
import ca.gbc.comp3095.taskservice.model.Task;
import ca.gbc.comp3095.taskservice.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private static final String TASK_SEQ = "task_sequence";

    private final TaskRepository repository;
    private final SequenceGeneratorService sequenceGenerator;

    public TaskServiceImpl(TaskRepository repository, SequenceGeneratorService sequenceGenerator) {
        this.repository = repository;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO request) {
        Task task = new Task();
        task.setTaskId(sequenceGenerator.getNextSequence(TASK_SEQ));

        task.setCourseId(request.getCourseId());
        task.setTitle(request.getTitle());
        task.setType(request.getType());
        task.setDueDate(request.getDueDate());

        task.setCompleted(request.isCompleted());
        task.setBonus(request.isBonus());
        task.setPriority(request.isPriority());

        task.setPriorityThresholdDays(request.getPriorityThresholdDays());
        task.setManualPriorityOverride(request.isManualPriorityOverride());

        task.setWeight(request.getWeight());
        task.setScorePercent(request.getScorePercent());

        Task saved = repository.save(task);
        return toResponse(saved);
    }

    @Override
    public TaskResponseDTO getTaskByTaskId(Long taskId) {
        Task task = repository.findByTaskId(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with taskId: " + taskId));
        return toResponse(task);
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO request) {
        Task task = repository.findByTaskId(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with taskId: " + taskId));

        task.setCourseId(request.getCourseId());
        task.setTitle(request.getTitle());
        task.setType(request.getType());
        task.setDueDate(request.getDueDate());

        task.setCompleted(request.isCompleted());
        task.setBonus(request.isBonus());
        task.setPriority(request.isPriority());

        task.setPriorityThresholdDays(request.getPriorityThresholdDays());
        task.setManualPriorityOverride(request.isManualPriorityOverride());

        task.setWeight(request.getWeight());
        task.setScorePercent(request.getScorePercent());

        Task saved = repository.save(task);
        return toResponse(saved);
    }

    @Override
    public void deleteTask(Long taskId) {
        if (!repository.existsByTaskId(taskId)) {
            throw new TaskNotFoundException("Task not found with taskId: " + taskId);
        }
        repository.deleteByTaskId(taskId);
    }

    @Override
    public List<TaskResponseDTO> getTasksByCourse(Long courseId) {
        return repository.findByCourseId(courseId).stream().map(this::toResponse).toList();
    }

    @Override
    public List<TaskResponseDTO> getTasksByCourseAndCompletion(Long courseId, boolean completed) {
        return repository.findByCourseIdAndIsCompleted(courseId, completed).stream().map(this::toResponse).toList();
    }

    private TaskResponseDTO toResponse(Task task) {
        return new TaskResponseDTO(
                task.getTaskId(),
                task.getCourseId(),
                task.getTitle(),
                task.getType(),
                task.getDueDate(),
                task.isCompleted(),
                task.isBonus(),
                task.isPriority(),
                task.getPriorityThresholdDays(),
                task.isManualPriorityOverride(),
                task.getWeight(),
                task.getScorePercent()
        );
    }
}
