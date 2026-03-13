package ca.gbc.comp3095.courseprogressservice.service;

import ca.gbc.comp3095.courseprogressservice.dto.CourseProgressRequestDTO;
import ca.gbc.comp3095.courseprogressservice.dto.CourseProgressResponseDTO;

import java.util.List;

public interface CourseProgressService {
    List<CourseProgressResponseDTO> getAllProgress();
    CourseProgressResponseDTO getProgressById(Long id);
    List<CourseProgressResponseDTO> getProgressByCourseId(Long courseId);
    CourseProgressResponseDTO createProgress(CourseProgressRequestDTO dto);
    CourseProgressResponseDTO updateProgress(Long id, CourseProgressRequestDTO dto);
    void deleteProgress(Long id);
}
