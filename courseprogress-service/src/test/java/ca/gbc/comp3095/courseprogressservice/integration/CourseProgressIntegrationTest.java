package ca.gbc.comp3095.courseprogressservice.integration;

import ca.gbc.comp3095.courseprogressservice.model.CourseProgress;
import ca.gbc.comp3095.courseprogressservice.repository.CourseProgressRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CourseProgressIntegrationTest {

    @Autowired
    private CourseProgressRepository courseProgressRepository;

    @Test
    void shouldPersistAndLoadCourseProgress() {
        CourseProgress progress = new CourseProgress(
                101L,
                80.0,
                70.0,
                10.0,
                100.0,
                80.0,
                true,
                LocalDate.now(),
                LocalDateTime.now()
        );

        courseProgressRepository.save(progress);

        assertThat(courseProgressRepository.findAll()).hasSize(1);
    }
}