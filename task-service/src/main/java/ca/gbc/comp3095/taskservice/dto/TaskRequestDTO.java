package ca.gbc.comp3095.taskservice.dto;

import ca.gbc.comp3095.taskservice.model.TaskType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TaskRequestDTO {

    @NotNull
    private Long courseId;

    @NotBlank
    private String title;

    @NotNull
    private TaskType type;

    @NotNull
    private LocalDateTime dueDate;

    private boolean isCompleted;
    private boolean isBonus;
    private boolean isPriority;

    private int priorityThresholdDays;
    private boolean manualPriorityOverride;

    private double weight;
    private double scorePercent;

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public TaskType getType() { return type; }
    public void setType(TaskType type) { this.type = type; }

    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }

    public boolean isBonus() { return isBonus; }
    public void setBonus(boolean bonus) { isBonus = bonus; }

    public boolean isPriority() { return isPriority; }
    public void setPriority(boolean priority) { isPriority = priority; }

    public int getPriorityThresholdDays() { return priorityThresholdDays; }
    public void setPriorityThresholdDays(int priorityThresholdDays) { this.priorityThresholdDays = priorityThresholdDays; }

    public boolean isManualPriorityOverride() { return manualPriorityOverride; }
    public void setManualPriorityOverride(boolean manualPriorityOverride) { this.manualPriorityOverride = manualPriorityOverride; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public double getScorePercent() { return scorePercent; }
    public void setScorePercent(double scorePercent) { this.scorePercent = scorePercent; }
}
