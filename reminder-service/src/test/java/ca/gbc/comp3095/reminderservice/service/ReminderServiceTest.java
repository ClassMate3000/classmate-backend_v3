package ca.gbc.comp3095.reminderservice.service;

import ca.gbc.comp3095.reminderservice.dto.ReminderRequestDTO;
import ca.gbc.comp3095.reminderservice.dto.ReminderResponseDTO;
import ca.gbc.comp3095.reminderservice.exception.ReminderNotFoundException;
import ca.gbc.comp3095.reminderservice.model.Reminder;
import ca.gbc.comp3095.reminderservice.repository.ReminderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReminderServiceTest {

    @Mock
    private ReminderRepository repository;

    @Mock
    private SequenceGeneratorService sequenceGenerator;

    @InjectMocks
    private ReminderServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateReminder() {

        ReminderRequestDTO request = new ReminderRequestDTO();
        request.setTaskId(101L);
        request.setMessage("Test Reminder");
        request.setScheduledAt(LocalDateTime.now());
        request.setWasSent(false);

        when(sequenceGenerator.getNextSequence(anyString())).thenReturn(1L);

        Reminder savedReminder = new Reminder();
        savedReminder.setReminderId(1L);
        savedReminder.setTaskId(101L);
        savedReminder.setMessage("Test Reminder");

        when(repository.save(any(Reminder.class))).thenReturn(savedReminder);

        ReminderResponseDTO response = service.createReminder(request);

        assertThat(response.getReminderId()).isEqualTo(1L);
        verify(repository).save(any(Reminder.class));
    }

    @Test
    void shouldReturnReminderById() {

        Reminder reminder = new Reminder();
        reminder.setReminderId(1L);

        when(repository.findByReminderId(1L)).thenReturn(Optional.of(reminder));

        ReminderResponseDTO response = service.getReminderByReminderId(1L);

        assertThat(response).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenReminderNotFound() {

        when(repository.findByReminderId(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getReminderByReminderId(1L))
                .isInstanceOf(ReminderNotFoundException.class);
    }

    @Test
    void shouldReturnAllReminders() {

        when(repository.findAll()).thenReturn(List.of(new Reminder(), new Reminder()));

        List<ReminderResponseDTO> result = service.getAllReminders();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldReturnRemindersByTaskId() {

        when(repository.findByTaskId(101L)).thenReturn(List.of(new Reminder()));

        List<ReminderResponseDTO> result = service.getRemindersByTaskId(101L);

        assertThat(result).hasSize(1);
    }

    @Test
    void shouldUpdateReminder() {

        Reminder existing = new Reminder();
        existing.setReminderId(1L);

        ReminderRequestDTO request = new ReminderRequestDTO();
        request.setTaskId(101L);
        request.setMessage("Updated Message");
        request.setScheduledAt(LocalDateTime.now());
        request.setWasSent(true);

        when(repository.findByReminderId(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(Reminder.class))).thenReturn(existing);

        ReminderResponseDTO response = service.updateReminder(1L, request);

        assertThat(response).isNotNull();
        verify(repository).save(existing);
    }

    @Test
    void shouldDeleteReminder() {

        when(repository.existsByReminderId(1L)).thenReturn(true);

        service.deleteReminder(1L);

        verify(repository).deleteByReminderId(1L);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingReminder() {

        when(repository.existsByReminderId(1L)).thenReturn(false);

        assertThatThrownBy(() -> service.deleteReminder(1L))
                .isInstanceOf(ReminderNotFoundException.class);
    }
}