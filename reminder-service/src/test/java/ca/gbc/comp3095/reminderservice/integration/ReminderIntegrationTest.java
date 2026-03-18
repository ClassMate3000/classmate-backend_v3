package ca.gbc.comp3095.reminderservice.integration;

import ca.gbc.comp3095.reminderservice.model.Reminder;
import ca.gbc.comp3095.reminderservice.repository.ReminderRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class ReminderIntegrationTest {

    @Container
    static MongoDBContainer mongo =
            new MongoDBContainer("mongo:7");

    @DynamicPropertySource
    static void configureMongo(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.data.mongodb.uri",
                mongo::getReplicaSetUrl
        );
    }

    @Autowired
    private ReminderRepository reminderRepository;

    @Test
    void shouldPersistAndLoadReminder() {
        // Updated to match current Reminder model — old fields (title, courseCode, priority, etc.) were removed.
        Reminder reminder = new Reminder(
                null,                                   // id — Mongo assigns this
                null,                                   // reminderId — assigned by service
                1001L,                                  // taskId
                "Midterm exam is approaching",          // message
                LocalDateTime.of(2026, 3, 1, 9, 0),    // scheduledAt
                false                                   // wasSent
        );

        reminderRepository.save(reminder);

        assertThat(reminderRepository.findAll()).hasSize(1);
    }
}