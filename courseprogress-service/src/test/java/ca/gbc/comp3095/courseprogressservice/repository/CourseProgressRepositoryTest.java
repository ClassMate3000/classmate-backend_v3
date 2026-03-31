package ca.gbc.comp3095.courseprogressservice.repository;

import ca.gbc.comp3095.courseprogressservice.model.CourseProgress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test") // THIS IS THE KEY FIX
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // forces H2
@TestPropertySource(properties = {
        "spring.flyway.enabled=false",              // disable Flyway
        "spring.jpa.hibernate.ddl-auto=create-drop" // let Hibernate create schema automatically
})
class CourseProgressRepositoryTest {

    @Autowired
    private CourseProgressRepository courseProgressRepository;

    private CourseProgress progress1;
    private CourseProgress progress2;

    @BeforeEach
    void setup() {
        courseProgressRepository.deleteAll();

        // Sample data using public constructor
        progress1 = new CourseProgress(
                100L,
                80.0,
                10.0,
                5.0,
                95.0,
                85.0,
                true,
                LocalDate.of(2026, 3, 31),
                LocalDateTime.of(2026, 3, 31, 12, 0)
        );

        progress2 = new CourseProgress(
                100L,
                90.0,
                5.0,
                2.0,
                95.0,
                88.0,
                true,
                LocalDate.of(2026, 4, 7),
                LocalDateTime.of(2026, 4, 7, 12, 0)
        );

        courseProgressRepository.save(progress1);
        courseProgressRepository.save(progress2);
    }

    @Test
    void testFindByCourseId() {
        List<CourseProgress> progressList = courseProgressRepository.findByCourseId(100L);

        assertThat(progressList).isNotNull();
        assertThat(progressList.size()).isEqualTo(2);
        assertThat(progressList).extracting("weekOf")
                .containsExactlyInAnyOrder(progress1.getWeekOf(), progress2.getWeekOf());
    }

    @Test
    void testExistsByCourseIdAndWeekOf() {
        boolean exists1 = courseProgressRepository.existsByCourseIdAndWeekOf(100L, LocalDate.of(2026, 3, 31));
        boolean exists2 = courseProgressRepository.existsByCourseIdAndWeekOf(100L, LocalDate.of(2026, 1, 1));
        boolean existsOtherCourse = courseProgressRepository.existsByCourseIdAndWeekOf(200L, LocalDate.of(2026, 3, 31));

        assertThat(exists1).isTrue();
        assertThat(exists2).isFalse();
        assertThat(existsOtherCourse).isFalse();
    }

    @Test
    void testSaveAndRetrieveProgress() {
        CourseProgress newProgress = new CourseProgress(
                200L,
                75.0,
                5.0,
                3.0,
                90.0,
                82.0,
                false,
                LocalDate.of(2026, 4, 14),
                LocalDateTime.of(2026, 4, 14, 12, 0)
        );

        CourseProgress savedProgress = courseProgressRepository.save(newProgress);
        assertThat(savedProgress.getProgressId()).isNotNull();

        CourseProgress retrieved = courseProgressRepository.findById(savedProgress.getProgressId()).orElse(null);
        assertThat(retrieved).isNotNull();
        assertThat(retrieved.getCourseId()).isEqualTo(200L);
        assertThat(retrieved.getWeekOf()).isEqualTo(LocalDate.of(2026, 4, 14));
        assertThat(retrieved.isCanMeetGoal()).isFalse();
    }

}