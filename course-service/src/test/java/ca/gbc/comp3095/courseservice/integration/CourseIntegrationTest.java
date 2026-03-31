package ca.gbc.comp3095.courseservice.integration;

import ca.gbc.comp3095.courseservice.model.Course;
import ca.gbc.comp3095.courseservice.model.CourseMeeting;
import ca.gbc.comp3095.courseservice.repository.CourseRepository;
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

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(properties = {
        "spring.flyway.enabled=false",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.default_schema=PUBLIC"
})
public class CourseIntegrationTest {

    @Autowired
    private CourseRepository courseRepository;

    private Course course;

    @BeforeEach
    void setUp() {
        courseRepository.deleteAll();

        course = new Course(
                "C101",
                "Test Course",
                "Test Instructor",
                new ArrayList<>(List.of(new CourseMeeting(1, LocalTime.of(9, 0), LocalTime.of(10, 0)))), // mutable
                90,
                LocalDate.now()
        );
    }

    @Test
    void testH2RepositorySaveAndFind() {
        Course saved = courseRepository.save(course);

        assertThat(saved.getCourseId()).isNotNull();
        assertThat(saved.getCode()).isEqualTo("C101");
        assertThat(saved.getTitle()).isEqualTo("Test Course");

        List<Course> allCourses = courseRepository.findAll();
        assertThat(allCourses).hasSize(1);
        assertThat(allCourses.get(0).getInstructor()).isEqualTo("Test Instructor");
    }
}