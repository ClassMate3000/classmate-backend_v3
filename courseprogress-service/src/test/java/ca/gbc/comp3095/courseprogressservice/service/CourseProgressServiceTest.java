package ca.gbc.comp3095.courseprogressservice.service;

import ca.gbc.comp3095.courseprogressservice.dto.CourseProgressRequestDTO;
import ca.gbc.comp3095.courseprogressservice.dto.CourseProgressResponseDTO;
import ca.gbc.comp3095.courseprogressservice.exception.CourseProgressNotFoundException;
import ca.gbc.comp3095.courseprogressservice.model.CourseProgress;
import ca.gbc.comp3095.courseprogressservice.repository.CourseProgressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test") // Use test profile
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // forces H2 in-memory DB for debug
class CourseProgressServiceTest {

    private CourseProgressRepository repository;
    private CourseProgressServiceImpl service;

    @BeforeEach
    void setUp() {
        // Mock repository for unit tests (no real DB required)
        repository = mock(CourseProgressRepository.class);
        service = new CourseProgressServiceImpl(repository);
    }


    // -------------------------------
    // Test getAllProgress()
    // -------------------------------
    @Test
    void testGetAllProgress() {
        // Prepare mock data
        CourseProgress progress = new CourseProgress(101L, 80.0, 70.0, 10.0, 100.0, 80.0,
                true, LocalDate.now(), LocalDateTime.now());
        when(repository.findAll()).thenReturn(List.of(progress));

        // Call service
        List<CourseProgressResponseDTO> result = service.getAllProgress();

        // Assertions
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCourseId()).isEqualTo(101L);

        // Verify repository call
        verify(repository, times(1)).findAll();
    }

    // -------------------------------
    // Test getProgressById() - Found
    // -------------------------------
    @Test
    void testGetProgressById_Found() {
        CourseProgress progress = new CourseProgress(101L, 80.0, 70.0, 10.0, 100.0, 80.0,
                true, LocalDate.now(), LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(progress));

        CourseProgressResponseDTO dto = service.getProgressById(1L);

        assertThat(dto.getCourseId()).isEqualTo(101L);
        verify(repository).findById(1L);
    }

    // -------------------------------
    // Test getProgressById() - Not Found
    // -------------------------------
    @Test
    void testGetProgressById_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CourseProgressNotFoundException.class, () -> service.getProgressById(1L));
        verify(repository).findById(1L);
    }

    // -------------------------------
    // Test getProgressByCourseId()
    // -------------------------------
    @Test
    void testGetProgressByCourseId() {
        CourseProgress progress = new CourseProgress(101L, 80.0, 70.0, 10.0, 100.0, 80.0,
                true, LocalDate.now(), LocalDateTime.now());
        when(repository.findByCourseId(101L)).thenReturn(List.of(progress));

        List<CourseProgressResponseDTO> result = service.getProgressByCourseId(101L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCourseId()).isEqualTo(101L);
        verify(repository).findByCourseId(101L);
    }

    // -------------------------------
    // Test createProgress()
    // -------------------------------
    @Test
    void testCreateProgress() {
        CourseProgressRequestDTO request = new CourseProgressRequestDTO();
        request.setCourseId(101L);
        request.setAccumulatedPercentPoints(80.0);
        request.setUsedPercentPoints(70.0);
        request.setLostPercentPoints(10.0);
        request.setMaxPossiblePercent(100.0);
        request.setCurrentGradePercent(80.0);
        request.setCanMeetGoal(true);
        request.setWeekOf(LocalDate.now());
        request.setComputedAt(LocalDateTime.now());

        CourseProgress saved = new CourseProgress(
                request.getCourseId(),
                request.getAccumulatedPercentPoints(),
                request.getUsedPercentPoints(),
                request.getLostPercentPoints(),
                request.getMaxPossiblePercent(),
                request.getCurrentGradePercent(),
                request.isCanMeetGoal(),
                request.getWeekOf(),
                request.getComputedAt()
        );

        when(repository.save(any(CourseProgress.class))).thenReturn(saved);

        CourseProgressResponseDTO dto = service.createProgress(request);

        assertThat(dto.getCourseId()).isEqualTo(101L);
        verify(repository).save(any(CourseProgress.class));
    }

    // -------------------------------
    // Test updateProgress() - Found
    // -------------------------------
    @Test
    void testUpdateProgress_Found() {
        CourseProgress existing = new CourseProgress(101L, 80.0, 70.0, 10.0, 100.0, 80.0,
                true, LocalDate.now(), LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(CourseProgress.class))).thenReturn(existing);

        CourseProgressRequestDTO request = new CourseProgressRequestDTO();
        request.setCourseId(102L);
        request.setAccumulatedPercentPoints(90.0);
        request.setUsedPercentPoints(85.0);
        request.setLostPercentPoints(5.0);
        request.setMaxPossiblePercent(100.0);
        request.setCurrentGradePercent(88.0);
        request.setCanMeetGoal(false);
        request.setWeekOf(LocalDate.now());
        request.setComputedAt(LocalDateTime.now());

        CourseProgressResponseDTO dto = service.updateProgress(1L, request);

        assertThat(dto.getCourseId()).isEqualTo(102L);
        assertThat(dto.isCanMeetGoal()).isFalse();
        verify(repository).findById(1L);
        verify(repository).save(any(CourseProgress.class));
    }

    // -------------------------------
    // Test updateProgress() - Not Found
    // -------------------------------
    @Test
    void testUpdateProgress_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        CourseProgressRequestDTO request = new CourseProgressRequestDTO();

        assertThrows(CourseProgressNotFoundException.class, () -> service.updateProgress(1L, request));
        verify(repository).findById(1L);
    }

    // -------------------------------
    // Test deleteProgress() - Found
    // -------------------------------
    @Test
    void testDeleteProgress_Found() {
        CourseProgress existing = new CourseProgress(101L, 80.0, 70.0, 10.0, 100.0, 80.0,
                true, LocalDate.now(), LocalDateTime.now());
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        doNothing().when(repository).delete(existing);

        service.deleteProgress(1L);

        verify(repository).findById(1L);
        verify(repository).delete(existing);
    }

    // -------------------------------
    // Test deleteProgress() - Not Found
    // -------------------------------
    @Test
    void testDeleteProgress_NotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CourseProgressNotFoundException.class, () -> service.deleteProgress(1L));
        verify(repository).findById(1L);
    }

}