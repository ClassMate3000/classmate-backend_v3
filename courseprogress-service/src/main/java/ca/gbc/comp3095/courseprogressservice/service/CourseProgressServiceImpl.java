package ca.gbc.comp3095.courseprogressservice.service;

import ca.gbc.comp3095.courseprogressservice.dto.CourseProgressRequestDTO;
import ca.gbc.comp3095.courseprogressservice.dto.CourseProgressResponseDTO;
import ca.gbc.comp3095.courseprogressservice.exception.CourseProgressNotFoundException;
import ca.gbc.comp3095.courseprogressservice.model.CourseProgress;
import ca.gbc.comp3095.courseprogressservice.repository.CourseProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseProgressServiceImpl implements CourseProgressService {

    private final CourseProgressRepository repository;

    public CourseProgressServiceImpl(CourseProgressRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CourseProgressResponseDTO> getAllProgress() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public CourseProgressResponseDTO getProgressById(Long id) {
        CourseProgress p = repository.findById(id)
                .orElseThrow(() -> new CourseProgressNotFoundException(id));
        return toResponse(p);
    }

    @Override
    public List<CourseProgressResponseDTO> getProgressByCourseId(Long courseId) {
        return repository.findByCourseId(courseId).stream().map(this::toResponse).toList();
    }

    @Override
    public CourseProgressResponseDTO createProgress(CourseProgressRequestDTO dto) {
        CourseProgress p = new CourseProgress(
                dto.getCourseId(),
                dto.getAccumulatedPercentPoints(),
                dto.getUsedPercentPoints(),
                dto.getLostPercentPoints(),
                dto.getMaxPossiblePercent(),
                dto.getCurrentGradePercent(),
                dto.isCanMeetGoal(),
                dto.getWeekOf(),
                dto.getComputedAt()
        );
        return toResponse(repository.save(p));
    }

    @Override
    public CourseProgressResponseDTO updateProgress(Long id, CourseProgressRequestDTO dto) {
        CourseProgress p = repository.findById(id)
                .orElseThrow(() -> new CourseProgressNotFoundException(id));

        p.setCourseId(dto.getCourseId());
        p.setAccumulatedPercentPoints(dto.getAccumulatedPercentPoints());
        p.setUsedPercentPoints(dto.getUsedPercentPoints());
        p.setLostPercentPoints(dto.getLostPercentPoints());
        p.setMaxPossiblePercent(dto.getMaxPossiblePercent());
        p.setCurrentGradePercent(dto.getCurrentGradePercent());
        p.setCanMeetGoal(dto.isCanMeetGoal());
        p.setWeekOf(dto.getWeekOf());
        p.setComputedAt(dto.getComputedAt());

        return toResponse(repository.save(p));
    }

    @Override
    public void deleteProgress(Long id) {
        CourseProgress p = repository.findById(id)
                .orElseThrow(() -> new CourseProgressNotFoundException(id));
        repository.delete(p);
    }

    private CourseProgressResponseDTO toResponse(CourseProgress p) {
        return new CourseProgressResponseDTO(
                p.getProgressId(),
                p.getCourseId(),
                p.getAccumulatedPercentPoints(),
                p.getUsedPercentPoints(),
                p.getLostPercentPoints(),
                p.getMaxPossiblePercent(),
                p.getCurrentGradePercent(),
                p.isCanMeetGoal(),
                p.getWeekOf(),
                p.getComputedAt()
        );
    }
}
