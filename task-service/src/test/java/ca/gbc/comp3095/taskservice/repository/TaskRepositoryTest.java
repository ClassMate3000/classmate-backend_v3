package ca.gbc.comp3095.taskservice.repository;

import ca.gbc.comp3095.taskservice.model.Task;
import ca.gbc.comp3095.taskservice.model.TaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void cleanDb() {
        taskRepository.deleteAll(); // clear the database before each test
    }

    // Create a sample Task
    private Task createSampleTask(Long taskId, Long courseId, String title) {
        Task task = new Task();
        task.setTaskId(taskId);
        task.setCourseId(courseId);
        task.setTitle(title);
        task.setType(TaskType.ASSIGNMENT);
        task.setDueDate(LocalDateTime.now().plusDays(7));
        task.setCompleted(false);
        task.setBonus(false);
        task.setPriority(false);
        task.setPriorityThresholdDays(0);
        task.setManualPriorityOverride(false);
        task.setWeight(0.0);
        task.setScorePercent(0.0);
        return task;
    }

    @Test
    void testSaveAndFindByTaskId() {
        Task task = createSampleTask(1L, 101L, "Test Task");
        taskRepository.save(task);

        Optional<Task> found = taskRepository.findByTaskId(1L);
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Test Task");
    }

    @Test
    void testExistsByTaskId() {
        Task task = createSampleTask(2L, 101L, "Existence Test");
        taskRepository.save(task);

        boolean exists = taskRepository.existsByTaskId(2L);
        assertThat(exists).isTrue();
    }

    @Test
    void testDeleteByTaskId() {
        Task task = createSampleTask(3L, 102L, "Delete Test");
        taskRepository.save(task);

        taskRepository.deleteByTaskId(3L);
        Optional<Task> deleted = taskRepository.findByTaskId(3L);
        assertThat(deleted).isNotPresent();
    }

    @Test
    void testFindByCourseId() {
        Task task1 = createSampleTask(4L, 200L, "Course Task 1");
        Task task2 = createSampleTask(5L, 200L, "Course Task 2");
        Task task3 = createSampleTask(6L, 201L, "Other Course Task");

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        List<Task> tasksForCourse200 = taskRepository.findByCourseId(200L);
        assertThat(tasksForCourse200).hasSize(2);
        assertThat(tasksForCourse200).extracting(Task::getTitle)
                .containsExactlyInAnyOrder("Course Task 1", "Course Task 2");
    }

    @Test
    void testFindByCourseIdAndIsCompleted() {
        Task task1 = createSampleTask(7L, 300L, "Incomplete Task");
        Task task2 = createSampleTask(8L, 300L, "Completed Task");
        task2.setCompleted(true);

        taskRepository.save(task1);
        taskRepository.save(task2);

        List<Task> incompleteTasks = taskRepository.findByCourseIdAndIsCompleted(300L, false);
        assertThat(incompleteTasks).hasSize(1);
        assertThat(incompleteTasks.get(0).getTitle()).isEqualTo("Incomplete Task");

        List<Task> completedTasks = taskRepository.findByCourseIdAndIsCompleted(300L, true);
        assertThat(completedTasks).hasSize(1);
        assertThat(completedTasks.get(0).getTitle()).isEqualTo("Completed Task");
    }
}