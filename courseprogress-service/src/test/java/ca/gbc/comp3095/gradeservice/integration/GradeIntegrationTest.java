package ca.gbc.comp3095.gradeservice.integration;

import ca.gbc.comp3095.courseprogressservice.model.CourseProgress;
import ca.gbc.comp3095.courseprogressservice.repository.CourseProgressRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class GradeIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass");

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private CourseProgressRepository courseProgressRepository;

    @Test
    void shouldPersistAndLoadCourseProgress() {
        // Updated to use CourseProgress — Grade class and GradeRepository do not exist.
        CourseProgress progress = new CourseProgress(
                1L,         // courseId
                20.0,       // accumulatedPercentPoints
                5.0,        // usedPercentPoints
                0.0,        // lostPercentPoints
                100.0,      // maxPossiblePercent
                85.0,       // currentGradePercent
                true,       // canMeetGoal
                LocalDate.of(2026, 2, 10),          // weekOf
                LocalDateTime.of(2026, 2, 10, 12, 0) // computedAt
        );

        courseProgressRepository.save(progress);

        assertThat(courseProgressRepository.findAll()).hasSize(1);
    }
}