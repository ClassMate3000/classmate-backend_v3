package ca.gbc.comp3095.courseservice.controller;

import ca.gbc.comp3095.courseservice.dto.CourseResponseDTO;
import ca.gbc.comp3095.courseservice.service.CourseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseController.class)
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void shouldReturnAllCourses() throws Exception {

        // Build a mock response that matches the current CourseResponseDTO constructor.
        List<CourseResponseDTO> mockCourses = List.of(
                new CourseResponseDTO(
                        1L,
                        "COMP3095",
                        "Capstone Project I",
                        "Prof. GBC",
                        List.of(),
                        85,
                        LocalDate.of(2026, 2, 10)
                )
        );

        Mockito.when(courseService.getAllCourses()).thenReturn(mockCourses);

        mockMvc.perform(get("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("COMP3095"));
    }
}
