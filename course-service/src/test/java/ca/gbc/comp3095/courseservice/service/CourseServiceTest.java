package ca.gbc.comp3095.courseservice.service;

import ca.gbc.comp3095.courseservice.dto.CourseRequestDTO;
import ca.gbc.comp3095.courseservice.model.Course;
import ca.gbc.comp3095.courseservice.model.CourseMeeting;
import ca.gbc.comp3095.courseservice.repository.CourseRepository;
import ca.gbc.comp3095.courseservice.service.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void shouldReturnAllCourses() {
        // Updated to match current Course constructor — description removed, instructor/meetings/gradeGoal/startWeek added.
        Course course = new Course(
                "COMP3095",
                "Microservices",
                "Prof. GBC",
                List.of(),
                85,
                LocalDate.of(2026, 2, 10)
        );

        when(courseRepository.findAll()).thenReturn(List.of(course));

        var result = courseService.getAllCourses();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCode()).isEqualTo("COMP3095");
    }

    @Test
    void shouldCreateCourse() {
        // Updated CourseRequestDTO — description replaced with instructor, meetings, gradeGoal, startWeek.
        CourseRequestDTO dto = new CourseRequestDTO();
        dto.setCode("COMP3095");
        dto.setTitle("Microservices");
        dto.setInstructor("Prof. GBC");
        dto.setMeetings(List.of());
        dto.setGradeGoal(85);
        dto.setStartWeek(LocalDate.of(2026, 2, 10));

        Course saved = new Course(
                "COMP3095",
                "Microservices",
                "Prof. GBC",
                List.of(),
                85,
                LocalDate.of(2026, 2, 10)
        );

        when(courseRepository.save(org.mockito.ArgumentMatchers.any()))
                .thenReturn(saved);

        var result = courseService.createCourse(dto);

        assertThat(result.getTitle()).isEqualTo("Microservices");
        assertThat(result.getCode()).isEqualTo("COMP3095");
    }
}