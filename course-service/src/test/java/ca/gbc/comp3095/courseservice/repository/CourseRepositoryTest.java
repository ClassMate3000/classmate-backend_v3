package ca.gbc.comp3095.courseservice.repository;

import ca.gbc.comp3095.courseservice.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    private Course course;

    @BeforeEach
    void setUp() {
        // Updated to match current Course constructor — description removed.
        course = new Course(
                "COMP3095",
                "Microservices",
                "Prof. GBC",
                List.of(),
                85,
                LocalDate.of(2026, 2, 10)
        );
    }

    @Test
    void shouldSaveCourse() {
        Course saved = courseRepository.save(course);

        // courseId is the correct field name — getId() was removed.
        assertThat(saved.getCourseId()).isNotNull();
        assertThat(saved.getCode()).isEqualTo("COMP3095");
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

        List<Course> courses = courseRepository.findAll();

        assertThat(courses).isNotEmpty();
        assertThat(courses.get(0).getCode()).isEqualTo("COMP3095");
    }

    @Test
    void shouldUpdateCourse() {
        Course saved = courseRepository.save(course);

        // description field was removed — updating title and instructor instead.
        saved.setTitle("Advanced Microservices");
        saved.setInstructor("Prof. Updated");
        Course updated = courseRepository.save(saved);

        assertThat(updated.getTitle()).isEqualTo("Advanced Microservices");
        assertThat(updated.getInstructor()).isEqualTo("Prof. Updated");
    }

    @Test
    void shouldDeleteCourse() {
        Course saved = courseRepository.save(course);

        courseRepository.delete(saved);

        Optional<Course> deleted = courseRepository.findById(saved.getCourseId());
        assertThat(deleted).isNotPresent();
    }
}