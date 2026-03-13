package ca.gbc.comp3095.reminderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reminders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {

    @Id
    private String id; // Mongo internal

    private Long reminderId; // UML

    private Long taskId;     // link to Task (microservice-friendly)

    private String message;

    private LocalDateTime scheduledAt;

    private boolean wasSent;
}
