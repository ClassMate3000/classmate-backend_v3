package ca.gbc.comp3095.courseprogressservice.controller;

import ca.gbc.comp3095.courseprogressservice.dto.CourseProgressRequestDTO;
import ca.gbc.comp3095.courseprogressservice.dto.CourseProgressResponseDTO;
import ca.gbc.comp3095.courseprogressservice.service.CourseProgressService;
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
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.closeTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourseProgressController.class)
@AutoConfigureMockMvc(addFilters = false) // <-- bypass security for tests
public class CourseProgressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseProgressService courseProgressService;

    @Autowired
    private ObjectMapper objectMapper;

    private CourseProgressResponseDTO sampleProgress;

    @BeforeEach
    void setup() {
        sampleProgress = new CourseProgressResponseDTO(
                1L, 100L, 80.0, 10.0, 5.0, 95.0, 85.0, true,
                LocalDate.of(2026, 3, 31),
                LocalDateTime.of(2026, 3, 31, 12, 0)
        );
    }

    @Test
    void testCreateProgress() throws Exception {
        CourseProgressRequestDTO requestDTO = new CourseProgressRequestDTO();
        requestDTO.setCourseId(100L);
        requestDTO.setAccumulatedPercentPoints(80.0);
        requestDTO.setUsedPercentPoints(10.0);
        requestDTO.setLostPercentPoints(5.0);
        requestDTO.setMaxPossiblePercent(95.0);
        requestDTO.setCurrentGradePercent(85.0);
        requestDTO.setCanMeetGoal(true);
        requestDTO.setWeekOf(LocalDate.of(2026, 3, 31));

        Mockito.when(courseProgressService.createProgress(any(CourseProgressRequestDTO.class)))
                .thenReturn(sampleProgress);

        mockMvc.perform(post("/api/v1/course-progress")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.progressId").value(sampleProgress.getProgressId()))
                .andExpect(jsonPath("$.courseId").value(sampleProgress.getCourseId()))
                .andExpect(jsonPath("$.accumulatedPercentPoints").value(closeTo(sampleProgress.getAccumulatedPercentPoints(), 0.001)))
                .andExpect(jsonPath("$.usedPercentPoints").value(closeTo(sampleProgress.getUsedPercentPoints(), 0.001)))
                .andExpect(jsonPath("$.lostPercentPoints").value(closeTo(sampleProgress.getLostPercentPoints(), 0.001)))
                .andExpect(jsonPath("$.maxPossiblePercent").value(closeTo(sampleProgress.getMaxPossiblePercent(), 0.001)))
                .andExpect(jsonPath("$.currentGradePercent").value(closeTo(sampleProgress.getCurrentGradePercent(), 0.001)))
                .andExpect(jsonPath("$.canMeetGoal").value(sampleProgress.isCanMeetGoal()))
                .andExpect(jsonPath("$.weekOf").value(sampleProgress.getWeekOf().toString()));
    }

    @Test
    void testUpdateProgress() throws Exception {
        CourseProgressRequestDTO requestDTO = new CourseProgressRequestDTO();
        requestDTO.setCourseId(100L);
        requestDTO.setAccumulatedPercentPoints(90.0);
        requestDTO.setUsedPercentPoints(5.0);
        requestDTO.setLostPercentPoints(2.0);
        requestDTO.setMaxPossiblePercent(95.0);
        requestDTO.setCurrentGradePercent(88.0);
        requestDTO.setCanMeetGoal(true);
        requestDTO.setWeekOf(LocalDate.of(2026, 3, 31));

        Mockito.when(courseProgressService.updateProgress(eq(1L), any(CourseProgressRequestDTO.class)))
                .thenReturn(sampleProgress);

        mockMvc.perform(put("/api/v1/course-progress/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.progressId").value(sampleProgress.getProgressId()))
                .andExpect(jsonPath("$.courseId").value(sampleProgress.getCourseId()))
                .andExpect(jsonPath("$.accumulatedPercentPoints").value(closeTo(sampleProgress.getAccumulatedPercentPoints(), 0.001)))
                .andExpect(jsonPath("$.usedPercentPoints").value(closeTo(sampleProgress.getUsedPercentPoints(), 0.001)))
                .andExpect(jsonPath("$.lostPercentPoints").value(closeTo(sampleProgress.getLostPercentPoints(), 0.001)))
                .andExpect(jsonPath("$.maxPossiblePercent").value(closeTo(sampleProgress.getMaxPossiblePercent(), 0.001)))
                .andExpect(jsonPath("$.currentGradePercent").value(closeTo(sampleProgress.getCurrentGradePercent(), 0.001)))
                .andExpect(jsonPath("$.canMeetGoal").value(sampleProgress.isCanMeetGoal()))
                .andExpect(jsonPath("$.weekOf").value(sampleProgress.getWeekOf().toString()));
    }
}