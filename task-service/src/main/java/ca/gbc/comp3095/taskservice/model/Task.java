package ca.gbc.comp3095.taskservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "task")
@Data // PENNY -  when using this should not have manual getters/setters/constructors
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    private String id; // Mongo internal ID

    // UML-required ID
    private Long taskId;

    private String title;
    private TaskType type;

    private LocalDateTime dueDate;

    private boolean isCompleted;
    private boolean isBonus;
    private boolean isPriority;

    private int priorityThresholdDays;
    private boolean manualPriorityOverride;

    private double weight;
    private double scorePercent;

    // Microservice reference (important)
    private Long courseId;
}
