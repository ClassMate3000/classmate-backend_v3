package ca.gbc.comp3095.reminderservice.repository;

import ca.gbc.comp3095.reminderservice.model.Reminder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest  // Only loads MongoDB-related beans
@ActiveProfiles("test") // Uses application-test.properties
class ReminderRepositoryTest {

    @Autowired
    private ReminderRepository reminderRepository;

    private Reminder reminder1;
    private Reminder reminder2;

    @BeforeEach
    void setUp() {
        // Clear database before each test
        reminderRepository.deleteAll();

        // Create sample reminders
        LocalDateTime dt1 = LocalDateTime.of(2026, 3, 31, 14, 0);
        LocalDateTime dt2 = LocalDateTime.of(2026, 4, 1, 14, 0);

        reminder1 = new Reminder(1L, 101L, "Finish assignment", dt1, false);
        reminder2 = new Reminder(2L, 102L, "Study for exam", dt2, false);

        // Save reminders to embedded MongoDB
        reminderRepository.save(reminder1);
        reminderRepository.save(reminder2);
    }

    @Test
    @DisplayName("Test findByReminderId returns correct reminder")
    void testFindByReminderId() {
        Optional<Reminder> found = reminderRepository.findByReminderId(1L);
        assertThat(found).isPresent();
        assertThat(found.get().getMessage()).isEqualTo("Finish assignment");
    }

    @Test
    @DisplayName("Test findAll returns all reminders")
    void testFindAllReminders() {
        List<Reminder> allReminders = reminderRepository.findAll();
        assertThat(allReminders).hasSize(2);
    }

    @Test
    @DisplayName("Test repository returns empty results when no reminders exist")
    void testEmptyRepository() {
        reminderRepository.deleteAll();

        List<Reminder> allReminders = reminderRepository.findAll();
        assertThat(allReminders).isEmpty();

        Optional<Reminder> reminderOpt = reminderRepository.findByReminderId(1L);
        assertThat(reminderOpt).isEmpty();
    }


}