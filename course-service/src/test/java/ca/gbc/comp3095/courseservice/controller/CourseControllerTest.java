package ca.gbc.comp3095.courseservice.controller;

import ca.gbc.comp3095.courseservice.dto.CourseRequestDTO;
import ca.gbc.comp3095.courseservice.dto.CourseResponseDTO;
import ca.gbc.comp3095.courseservice.service.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Autowired
    private ObjectMapper objectMapper;

    private CourseResponseDTO sampleCourseResponse;
    private CourseRequestDTO sampleCourseRequest;

    @BeforeEach
    void setUp() {
        // Sample response DTO
        sampleCourseResponse = new CourseResponseDTO(
                1L,
                "COMP3095",
                "Microservices",
                "Dr. Smith",
                List.of(new CourseResponseDTO.MeetingDTO(1, LocalTime.of(9, 0), LocalTime.of(10, 30))),
                90,
                LocalDate.now()
        );

        // Sample request DTO
        CourseRequestDTO.MeetingDTO meetingRequest = new CourseRequestDTO.MeetingDTO();
        meetingRequest.setDayOfWeek(1);
        meetingRequest.setStartTime(LocalTime.of(9, 0));
        meetingRequest.setEndTime(LocalTime.of(10, 30));

        sampleCourseRequest = new CourseRequestDTO();
        sampleCourseRequest.setCode("COMP3095");
        sampleCourseRequest.setTitle("Microservices");
        sampleCourseRequest.setInstructor("Dr. Smith");
        sampleCourseRequest.setGradeGoal(90);
        sampleCourseRequest.setStartWeek(LocalDate.now());
        sampleCourseRequest.setMeetings(List.of(meetingRequest));
    }

    @Test
    void shouldReturnHealthStatus() throws Exception {
        mockMvc.perform(get("/api/v1/courses/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("UP"));
    }

    @Test
    void shouldReturnAllCourses() throws Exception {
        // Arrange (mock service)
        Mockito.when(courseService.getAllCourses())
                .thenReturn(List.of(sampleCourseResponse));

        // Act + Assert
        mockMvc.perform(get("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseId").value(1))
                .andExpect(jsonPath("$[0].code").value("COMP3095"))
                .andExpect(jsonPath("$[0].title").value("Microservices"))
                .andExpect(jsonPath("$[0].instructor").value("Dr. Smith"))
                .andExpect(jsonPath("$[0].gradeGoal").value(90));
    }

    @Test
    void shouldReturnCourseById() throws Exception {
        // Arrange (mock service)
        Mockito.when(courseService.getCourseById(1L))
                .thenReturn(sampleCourseResponse);

        // Act + Assert
        mockMvc.perform(get("/api/v1/courses/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$.code").value("COMP3095"))
                .andExpect(jsonPath("$.title").value("Microservices"))
                .andExpect(jsonPath("$.instructor").value("Dr. Smith"));
    }

    @Test
    void shouldCreateCourse() throws Exception {
        // Arrange (mock service)
        Mockito.when(courseService.createCourse(any(CourseRequestDTO.class)))
                .thenReturn(sampleCourseResponse);

        // Convert request DTO to JSON
        String requestJson = objectMapper.writeValueAsString(sampleCourseRequest);

        // Act + Assert
        mockMvc.perform(post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$.code").value("COMP3095"))
                .andExpect(jsonPath("$.title").value("Microservices"))
                .andExpect(jsonPath("$.instructor").value("Dr. Smith"));
    }

    @Test
    void shouldUpdateCourse() throws Exception {

        CourseResponseDTO updatedResponse = new CourseResponseDTO(
                1L,
                "COMP3095",
                "Advanced Microservices",
                "Dr. Smith",
                List.of(),
                95,
                LocalDate.now()
        );

        CourseRequestDTO updatedRequest = new CourseRequestDTO();
        updatedRequest.setCode("COMP3095");
        updatedRequest.setTitle("Advanced Microservices");
        updatedRequest.setInstructor("Dr. Smith");
        updatedRequest.setGradeGoal(95);
        updatedRequest.setStartWeek(LocalDate.now());

        // Add valid meeting
        CourseRequestDTO.MeetingDTO meeting = new CourseRequestDTO.MeetingDTO();
        meeting.setDayOfWeek(1);
        meeting.setStartTime(LocalTime.of(9, 0));
        meeting.setEndTime(LocalTime.of(10, 30));

        updatedRequest.setMeetings(List.of(meeting));

        Mockito.when(courseService.updateCourse(eq(1L), any(CourseRequestDTO.class)))
                .thenReturn(updatedResponse);

        String requestJson = objectMapper.writeValueAsString(updatedRequest);

        mockMvc.perform(put("/api/v1/courses/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Advanced Microservices"));
    }

    @Test
    void shouldDeleteCourse() throws Exception {
        // Arrange
        Mockito.doNothing().when(courseService).deleteCourse(1L);

        // Act + Assert
        mockMvc.perform(delete("/api/v1/courses/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldFailValidation_whenMissingFields() throws Exception {
        // Empty JSON → should fail validation
        String emptyJson = "{}";

        mockMvc.perform(post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyJson))
                .andExpect(status().isBadRequest());
    }
}