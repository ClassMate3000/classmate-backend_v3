package ca.gbc.comp3095.taskservice.integration;

import ca.gbc.comp3095.taskservice.model.Task;
import ca.gbc.comp3095.taskservice.model.TaskType;
import ca.gbc.comp3095.taskservice.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class TaskIntegrationTest {

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:7");

    @DynamicPropertySource
    static void configureMongo(DynamicPropertyRegistry registry) {
        registry.add(
                "spring.data.mongodb.uri",
                mongo::getReplicaSetUrl
        );
    }

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldPersistAndLoadTask() {

        Task task = new Task();
        task.setTaskId(1L);
        task.setTitle("Integration Test Task");
        task.setType(TaskType.ASSIGNMENT);
        task.setDueDate(LocalDateTime.now());
        task.setCompleted(false);
        task.setBonus(false);
        task.setPriority(false);
        task.setPriorityThresholdDays(0);
        task.setManualPriorityOverride(false);
        task.setWeight(0.0);
        task.setScorePercent(0.0);
        task.setCourseId(101L);

        taskRepository.save(task);

        assertThat(taskRepository.findAll()).hasSize(1);

        Task loaded = taskRepository.findAll().get(0);
        assertThat(loaded.getTitle()).isEqualTo("Integration Test Task");
        assertThat(loaded.getType()).isEqualTo(TaskType.ASSIGNMENT);
        assertThat(loaded.getCourseId()).isEqualTo(101L);
    }
}