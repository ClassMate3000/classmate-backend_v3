package ca.gbc.comp3095.courseservice.service;

import ca.gbc.comp3095.courseservice.dto.CourseRequestDTO;
import ca.gbc.comp3095.courseservice.dto.CourseResponseDTO;
import ca.gbc.comp3095.courseservice.model.Course;
import ca.gbc.comp3095.courseservice.model.CourseMeeting;
import ca.gbc.comp3095.courseservice.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private CourseRequestDTO dto;
    private Course course;

    @BeforeEach
    void setUp() {
        // Sample CourseRequestDTO
        dto = new CourseRequestDTO();
        dto.setCode("COMP3095");
        dto.setTitle("Microservices");
        dto.setInstructor("Spring Boot");
        // Use no-arg constructor + setters for MeetingDTO
        CourseRequestDTO.MeetingDTO meetingDTO = new CourseRequestDTO.MeetingDTO();
        meetingDTO.setDayOfWeek(1);
        meetingDTO.setStartTime(LocalTime.of(9, 0));
        meetingDTO.setEndTime(LocalTime.of(10, 0));
        dto.setMeetings(List.of(meetingDTO));
        dto.setGradeGoal(85);
        dto.setStartWeek(LocalDate.now());

        // Corresponding Course entity
        CourseMeeting courseMeeting = new CourseMeeting(1, LocalTime.of(9, 0), LocalTime.of(10, 0));
        List<CourseMeeting> meetings = new ArrayList<>();
        meetings.add(courseMeeting);

        course = new Course(
                dto.getCode(),
                dto.getTitle(),
                dto.getInstructor(),
                meetings,
                dto.getGradeGoal(),
                dto.getStartWeek()
        );
        // No need to call setCourseId(); Mockito will mock repository save
    }

    @Test
    void shouldReturnAllCourses() {
        when(courseRepository.findAll()).thenReturn(List.of(course));

        List<CourseResponseDTO> result = courseService.getAllCourses();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCode()).isEqualTo("COMP3095");
        assertThat(result.get(0).getInstructor()).isEqualTo("Spring Boot");
        assertThat(result.get(0).getMeetings()).hasSize(1);
        assertThat(result.get(0).getGradeGoal()).isEqualTo(85);
        assertThat(result.get(0).getStartWeek()).isEqualTo(dto.getStartWeek());
    }

    @Test
    void shouldCreateCourse() {
        // Mock repository to return a Course with ID set internally
        Course savedCourse = new Course(
                course.getCode(),
                course.getTitle(),
                course.getInstructor(),
                course.getMeetings(),
                course.getGradeGoal(),
                course.getStartWeek()
        );
        // Mock generated ID inside save
        when(courseRepository.save(ArgumentMatchers.any(Course.class))).thenAnswer(invocation -> {
            Course arg = invocation.getArgument(0);
            return new Course(
                    arg.getCode(),
                    arg.getTitle(),
                    arg.getInstructor(),
                    arg.getMeetings(),
                    arg.getGradeGoal(),
                    arg.getStartWeek()
            ); // ID handled internally by CourseResponseDTO
        });

        CourseResponseDTO result = courseService.createCourse(dto);

        assertThat(result.getCode()).isEqualTo("COMP3095");
        assertThat(result.getTitle()).isEqualTo("Microservices");
        assertThat(result.getInstructor()).isEqualTo("Spring Boot");
        assertThat(result.getMeetings()).hasSize(1);
        assertThat(result.getGradeGoal()).isEqualTo(85);
        assertThat(result.getStartWeek()).isEqualTo(dto.getStartWeek());

        verify(courseRepository, times(1)).save(ArgumentMatchers.any(Course.class));
    }

    @Test
    void shouldGetCourseById() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        CourseResponseDTO result = courseService.getCourseById(1L);

        assertThat(result.getCode()).isEqualTo("COMP3095");
        assertThat(result.getMeetings()).hasSize(1);

        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void shouldUpdateCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(courseRepository.save(ArgumentMatchers.any(Course.class))).thenReturn(course);

        dto.setTitle("Advanced Microservices"); // Update the title

        CourseResponseDTO result = courseService.updateCourse(1L, dto);

        assertThat(result.getTitle()).isEqualTo("Advanced Microservices");
        assertThat(result.getCode()).isEqualTo("COMP3095");

        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).save(ArgumentMatchers.any(Course.class));
    }

    @Test
    void shouldDeleteCourse() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        doNothing().when(courseRepository).delete(course);

        courseService.deleteCourse(1L);

        verify(courseRepository, times(1)).findById(1L);
        verify(courseRepository, times(1)).delete(course);
    }
}