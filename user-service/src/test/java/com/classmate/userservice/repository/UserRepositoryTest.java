package com.classmate.userservice.repository;

import com.classmate.userservice.controller.ProtectedController;
import com.classmate.userservice.model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // =========================
    // TEST: SAVE + FIND BY EMAIL
    // =========================
    @Test
    void testFindByEmail() {

        // Arrange
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");

        userRepository.save(user);

        // Act
        Optional<User> foundUser = userRepository.findByEmail("john@example.com");

        // Assert
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getEmail()).isEqualTo("john@example.com");
    }

    // =========================
    // TEST: EXISTS BY EMAIL (TRUE)
    // =========================
    @Test
    void testExistsByEmailTrue() {

        // Arrange
        User user = new User();
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setEmail("jane@example.com");
        user.setPassword("password123");

        userRepository.save(user);

        // Act
        boolean exists = userRepository.existsByEmail("jane@example.com");

        // Assert
        assertThat(exists).isTrue();
    }

    // =========================
    // TEST: EXISTS BY EMAIL (FALSE)
    // =========================
    @Test
    void testExistsByEmailFalse() {

        // Act
        boolean exists = userRepository.existsByEmail("notfound@example.com");

        // Assert
        assertThat(exists).isFalse();
    }


}