package ca.gbc.comp3095.reminderservice.dto;

import java.time.LocalDateTime;

public class ReminderResponseDTO {

    private final Long reminderId;
    private final Long taskId;
    private final String message;
    private final LocalDateTime scheduledAt;
    private final boolean wasSent;

    public ReminderResponseDTO(Long reminderId, Long taskId, String message, LocalDateTime scheduledAt, boolean wasSent) {
        this.reminderId = reminderId;
        this.taskId = taskId;
        this.message = message;
        this.scheduledAt = scheduledAt;
        this.wasSent = wasSent;
    }

    public Long getReminderId() { return reminderId; }
    public Long getTaskId() { return taskId; }
    public String getMessage() { return message; }
    public LocalDateTime getScheduledAt() { return scheduledAt; }
    public boolean isWasSent() { return wasSent; }
}
