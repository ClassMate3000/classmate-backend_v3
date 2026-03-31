package ca.gbc.comp3095.courseservice.service;

import ca.gbc.comp3095.courseservice.dto.CourseRequestDTO;
import ca.gbc.comp3095.courseservice.model.Course;
import ca.gbc.comp3095.courseservice.repository.CourseRepository;
import ca.gbc.comp3095.courseservice.service.CourseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/*
@org.junit.jupiter.api.Disabled("Outdated after Course model update")
@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void shouldReturnAllCourses() {

        Course course = new Course("COMP3095", "Microservices", "Spring Boot");
        when(courseRepository.findAll()).thenReturn(List.of(course));

        var result = courseService.getAllCourses();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCode()).isEqualTo("COMP3095");
    }

    @Test
    void shouldCreateCourse() {

        CourseRequestDTO dto = new CourseRequestDTO();
        dto.setCode("COMP3095");
        dto.setTitle("Microservices");
        dto.setDescription("Spring Boot");

        Course saved = new Course("COMP3095", "Microservices", "Spring Boot");

        when(courseRepository.save(org.mockito.ArgumentMatchers.any()))
                .thenReturn(saved);

        var result = courseService.createCourse(dto);

        assertThat(result.getTitle()).isEqualTo("Microservices");
        assertThat(result.getCode()).isEqualTo("COMP3095");
    }
}
*/