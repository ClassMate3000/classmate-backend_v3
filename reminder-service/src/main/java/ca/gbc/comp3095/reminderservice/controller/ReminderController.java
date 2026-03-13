package ca.gbc.comp3095.reminderservice.controller;

import ca.gbc.comp3095.reminderservice.dto.ReminderRequestDTO;
import ca.gbc.comp3095.reminderservice.dto.ReminderResponseDTO;
import ca.gbc.comp3095.reminderservice.service.ReminderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reminders")
public class ReminderController {

    private final ReminderService service;

    public ReminderController(ReminderService service) {
        this.service = service;
    }

    @PostMapping
    public ReminderResponseDTO createReminder(@Valid @RequestBody ReminderRequestDTO request) {
        return service.createReminder(request);
    }

    @GetMapping("/{reminderId}")
    public ReminderResponseDTO getReminderById(@PathVariable Long reminderId) {
        return service.getReminderByReminderId(reminderId);
    }

    @GetMapping
    public List<ReminderResponseDTO> getAllReminders() {
        return service.getAllReminders();
    }

    @GetMapping("/task/{taskId}")
    public List<ReminderResponseDTO> getRemindersByTask(@PathVariable Long taskId) {
        return service.getRemindersByTaskId(taskId);
    }

    @PutMapping("/{reminderId}")
    public ReminderResponseDTO updateReminder(@PathVariable Long reminderId,
                                             @Valid @RequestBody ReminderRequestDTO request) {
        return service.updateReminder(reminderId, request);
    }

    @DeleteMapping("/{reminderId}")
    public void deleteReminder(@PathVariable Long reminderId) {
        service.deleteReminder(reminderId);
    }
}
