package ca.gbc.comp3095.courseservice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ca.gbc.comp3095.courseservice.repository.CourseRepository;
import ca.gbc.comp3095.courseservice.model.Course;

import static org.assertj.core.api.Assertions.assertThat;

/*
@org.junit.jupiter.api.Disabled("Outdated after Course model update")


@SpringBootTest
@Testcontainers
public class CourseIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void testPostgresRepository() {
        courseRepository.save(new Course("C101", "Test Course", "This is a test course"));
        assertThat(courseRepository.findAll()).isNotEmpty();
    }
}
*/
