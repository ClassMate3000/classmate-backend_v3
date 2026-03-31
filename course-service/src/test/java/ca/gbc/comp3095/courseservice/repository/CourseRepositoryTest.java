package ca.gbc.comp3095.courseservice.repository;

import ca.gbc.comp3095.courseservice.model.Course;
import ca.gbc.comp3095.courseservice.model.CourseMeeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.properties.hibernate.default_schema=PUBLIC",
        "spring.jpa.show-sql=true"
})
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    private Course course;

    @BeforeEach
    void setUp() {
        courseRepository.deleteAll();

        course = new Course(
                "COMP3095",
                "Microservices",
                "Spring Boot",
                new ArrayList<>(List.of(new CourseMeeting(1, LocalTime.of(9,0), LocalTime.of(10,0)))), // mutable
                85,
                LocalDate.now()
        );
    }

    @Test
    void shouldSaveCourse() {
        Course saved = courseRepository.save(course);

        assertThat(saved.getCourseId()).isNotNull();
        assertThat(saved.getCode()).isEqualTo("COMP3095");
        assertThat(saved.getTitle()).isEqualTo("Microservices");
        assertThat(saved.getInstructor()).isEqualTo("Spring Boot");
        assertThat(saved.getMeetings()).hasSize(1);
        assertThat(saved.getGradeGoal()).isEqualTo(85);
        assertThat(saved.getStartWeek()).isEqualTo(course.getStartWeek());
    }

    @Test
    void shouldFindCourseById() {
        Course saved = courseRepository.save(course);

        Optional<Course> found = courseRepository.findById(saved.getCourseId());

        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Microservices");
    }

    @Test
    void shouldFindAllCourses() {
        courseRepository.save(course);

        var courses = courseRepository.findAll();

        assertThat(courses).hasSize(1);
        assertThat(courses.get(0).getCode()).isEqualTo("COMP3095");
    }


    @Test
    void shouldUpdateCourse() {
        Course saved = courseRepository.save(course);

        saved.setTitle("Advanced Microservices");
        saved.setGradeGoal(90);
        Course updated = courseRepository.save(saved);

        assertThat(updated.getTitle()).isEqualTo("Advanced Microservices");
        assertThat(updated.getGradeGoal()).isEqualTo(90);
    }


    @Test
    void shouldDeleteCourse() {
        Course saved = courseRepository.save(course);

        courseRepository.delete(saved);

        Optional<Course> deleted = courseRepository.findById(saved.getCourseId());
        assertThat(deleted).isNotPresent();
    }
}
