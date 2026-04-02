package ca.gbc.comp3095.apigateway.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test") // Loads src/test/resources/application-test.properties
@AutoConfigureWebTestClient
class GatewayRoutingConfigTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCoursesRouteExists() {
        webTestClient.get()
                .uri("/api/v1/courses/test")
                .exchange()
                .expectStatus()
                .is5xxServerError(); // backend not running → proves route exists
    }


    @Test
    void testTasksRouteExists() {
        webTestClient.get()
                .uri("/api/v1/tasks/test")
                .exchange()
                .expectStatus()
                .is5xxServerError(); // backend not running → proves route exists
    }


}