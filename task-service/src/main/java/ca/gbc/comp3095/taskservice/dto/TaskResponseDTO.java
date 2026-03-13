package ca.gbc.comp3095.taskservice.dto;

import ca.gbc.comp3095.taskservice.model.TaskType;

import java.time.LocalDateTime;

public class TaskResponseDTO {

    private final Long taskId;
    private final Long courseId;

    private final String title;
    private final TaskType type;
    private final LocalDateTime dueDate;

    private final boolean isCompleted;
    private final boolean isBonus;
    private final boolean isPriority;

    private final int priorityThresholdDays;
    private final boolean manualPriorityOverride;

    private final double weight;
    private final double scorePercent;

    public TaskResponseDTO(Long taskId, Long courseId, String title, TaskType type, LocalDateTime dueDate,
                           boolean isCompleted, boolean isBonus, boolean isPriority,
                           int priorityThresholdDays, boolean manualPriorityOverride,
                           double weight, double scorePercent) {
        this.taskId = taskId;
        this.courseId = courseId;
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
        this.isBonus = isBonus;
        this.isPriority = isPriority;
        this.priorityThresholdDays = priorityThresholdDays;
        this.manualPriorityOverride = manualPriorityOverride;
        this.weight = weight;
        this.scorePercent = scorePercent;
    }

    public Long getTaskId() { return taskId; }
    public Long getCourseId() { return courseId; }

    public String getTitle() { return title; }
    public TaskType getType() { return type; }
    public LocalDateTime getDueDate() { return dueDate; }

    public boolean isCompleted() { return isCompleted; }
    public boolean isBonus() { return isBonus; }
    public boolean isPriority() { return isPriority; }

    public int getPriorityThresholdDays() { return priorityThresholdDays; }
    public boolean isManualPriorityOverride() { return manualPriorityOverride; }

    public double getWeight() { return weight; }
    public double getScorePercent() { return scorePercent; }
}
