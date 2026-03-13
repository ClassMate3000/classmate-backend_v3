package ca.gbc.comp3095.reminderservice.service;

import ca.gbc.comp3095.reminderservice.dto.ReminderRequestDTO;
import ca.gbc.comp3095.reminderservice.dto.ReminderResponseDTO;

import java.util.List;

public interface ReminderService {

    ReminderResponseDTO createReminder(ReminderRequestDTO requestDTO);

    ReminderResponseDTO getReminderByReminderId(Long reminderId);

    List<ReminderResponseDTO> getAllReminders();

    List<ReminderResponseDTO> getRemindersByTaskId(Long taskId);

    ReminderResponseDTO updateReminder(Long reminderId, ReminderRequestDTO requestDTO);

    void deleteReminder(Long reminderId);
}
