package ca.gbc.comp3095.courseprogressservice.repository;

import ca.gbc.comp3095.courseprogressservice.model.CourseProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CourseProgressRepository extends JpaRepository<CourseProgress, Long> {

    List<CourseProgress> findByCourseId(Long courseId);

    boolean existsByCourseIdAndWeekOf(Long courseId, LocalDate weekOf);
}
