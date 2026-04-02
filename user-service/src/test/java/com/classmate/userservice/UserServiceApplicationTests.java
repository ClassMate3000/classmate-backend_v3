package com.classmate.userservice;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;



// @Disabled("Disabled: no datasource configured for tests")
@ActiveProfiles("test")
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "jwt.secret=12345678901234567890123456789012", // 32 chars for HS256,
        "spring.flyway.enabled=false"
})
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
        // Ensures the Spring context starts up correctly
    }
}