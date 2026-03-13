package ca.gbc.comp3095.gradeservice.integration;

import ca.gbc.comp3095.gradeservice.model.Grade;
import ca.gbc.comp3095.gradeservice.repository.GradeRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class GradeIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass");

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private GradeRepository gradeRepository;

    @Test
    void shouldPersistAndLoadGrade() {
        Grade grade = new Grade(
                "C101",
                "Intro to Testing",
                "Exam",
                92.5,
                0.4,
                "Excellent"
        );

        gradeRepository.save(grade);

        assertThat(gradeRepository.findAll()).hasSize(1);
    }
}
