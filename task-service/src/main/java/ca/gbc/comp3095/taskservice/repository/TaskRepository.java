package ca.gbc.comp3095.taskservice.repository;

import ca.gbc.comp3095.taskservice.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {

    Optional<Task> findByTaskId(Long taskId);

    boolean existsByTaskId(Long taskId);

    void deleteByTaskId(Long taskId);

    List<Task> findByCourseId(Long courseId);

    List<Task> findByCourseIdAndIsCompleted(Long courseId, boolean isCompleted);
}
