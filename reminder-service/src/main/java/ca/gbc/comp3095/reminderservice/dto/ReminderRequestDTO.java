package ca.gbc.comp3095.reminderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ReminderRequestDTO {

    @NotNull
    private Long taskId;

    @NotBlank
    private String message;

    @NotNull
    private LocalDateTime scheduledAt;

    private boolean wasSent;

    public Long getTaskId() { return taskId; }
    public void setTaskId(Long taskId) { this.taskId = taskId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt) { this.scheduledAt = scheduledAt; }

    public boolean isWasSent() { return wasSent; }
    public void setWasSent(boolean wasSent) { this.wasSent = wasSent; }
}
