package ca.gbc.comp3095.reminderservice.service;

import ca.gbc.comp3095.reminderservice.dto.ReminderRequestDTO;
import ca.gbc.comp3095.reminderservice.dto.ReminderResponseDTO;
import ca.gbc.comp3095.reminderservice.exception.ReminderNotFoundException;
import ca.gbc.comp3095.reminderservice.model.Reminder;
import ca.gbc.comp3095.reminderservice.repository.ReminderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService {

    private static final String REMINDER_SEQ = "reminder_sequence";

    private final ReminderRepository repository;
    private final SequenceGeneratorService sequenceGenerator;

    public ReminderServiceImpl(ReminderRepository repository, SequenceGeneratorService sequenceGenerator) {
        this.repository = repository;
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public ReminderResponseDTO createReminder(ReminderRequestDTO request) {
        Reminder reminder = new Reminder();
        reminder.setReminderId(sequenceGenerator.getNextSequence(REMINDER_SEQ));

        reminder.setTaskId(request.getTaskId());
        reminder.setMessage(request.getMessage());
        reminder.setScheduledAt(request.getScheduledAt());
        reminder.setWasSent(request.isWasSent());

        Reminder saved = repository.save(reminder);
        return toResponse(saved);
    }

    @Override
    public ReminderResponseDTO getReminderByReminderId(Long reminderId) {
        Reminder reminder = repository.findByReminderId(reminderId)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with reminderId: " + reminderId));
        return toResponse(reminder);
    }

    @Override
    public List<ReminderResponseDTO> getAllReminders() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public List<ReminderResponseDTO> getRemindersByTaskId(Long taskId) {
        return repository.findByTaskId(taskId).stream().map(this::toResponse).toList();
    }

    @Override
    public ReminderResponseDTO updateReminder(Long reminderId, ReminderRequestDTO request) {
        Reminder reminder = repository.findByReminderId(reminderId)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with reminderId: " + reminderId));

        reminder.setTaskId(request.getTaskId());
        reminder.setMessage(request.getMessage());
        reminder.setScheduledAt(request.getScheduledAt());
        reminder.setWasSent(request.isWasSent());

        Reminder saved = repository.save(reminder);
        return toResponse(saved);
    }

    @Override
    public void deleteReminder(Long reminderId) {
        if (!repository.existsByReminderId(reminderId)) {
            throw new ReminderNotFoundException("Reminder not found with reminderId: " + reminderId);
        }
        repository.deleteByReminderId(reminderId);
    }

    private ReminderResponseDTO toResponse(Reminder reminder) {
        return new ReminderResponseDTO(
                reminder.getReminderId(),
                reminder.getTaskId(),
                reminder.getMessage(),
                reminder.getScheduledAt(),
                reminder.isWasSent()
        );
    }
}
