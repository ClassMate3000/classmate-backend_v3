package com.classmate.userservice.service;

import com.classmate.userservice.dto.AuthResponseDTO;
import com.classmate.userservice.dto.LoginRequestDTO;
import com.classmate.userservice.dto.RegisterRequestDTO;
import com.classmate.userservice.model.User;
import com.classmate.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// ============================================================
// PURPOSE: Unit tests for service layer (register + login)
// ============================================================
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        // Sample user used in tests
        sampleUser = new User();
        sampleUser.setUserId(1L);
        sampleUser.setFirstName("John");
        sampleUser.setLastName("Doe");
        sampleUser.setEmail("john@example.com");
        sampleUser.setPassword("encodedPassword");
    }

    @Test
    void testRegisterSuccess() {
        // Arrange
        RegisterRequestDTO request = new RegisterRequestDTO(
                "John", "Doe", "john@example.com", "password123"
        );

        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);
        when(jwtService.generateToken(sampleUser.getEmail())).thenReturn("token123");

        // Act
        AuthResponseDTO response = authService.register(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.userId()).isEqualTo(sampleUser.getUserId());
        assertThat(response.token()).isEqualTo("token123");

        // Verify repository interaction
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterEmailAlreadyExists() {
        // Arrange
        RegisterRequestDTO request = new RegisterRequestDTO(
                "John", "Doe", "john@example.com", "password123"
        );

        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(request);
        });

        assertThat(exception.getMessage()).isEqualTo("Email already in use");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginSuccess() {
        // Arrange
        LoginRequestDTO request = new LoginRequestDTO("john@example.com", "password123");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.matches(request.password(), sampleUser.getPassword())).thenReturn(true);
        when(jwtService.generateToken(sampleUser.getEmail())).thenReturn("token123");

        // Act
        AuthResponseDTO response = authService.login(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.userId()).isEqualTo(sampleUser.getUserId());
        assertThat(response.token()).isEqualTo("token123");
    }

    @Test
    void testLoginInvalidEmail() {
        // Arrange
        LoginRequestDTO request = new LoginRequestDTO("wrong@example.com", "password123");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertThat(exception.getMessage()).isEqualTo("invalid credentials");
    }

    @Test
    void testLoginInvalidPassword() {
        // Arrange
        LoginRequestDTO request = new LoginRequestDTO("john@example.com", "wrongPassword");

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.matches(request.password(), sampleUser.getPassword())).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        assertThat(exception.getMessage()).isEqualTo("invalid credentials");
    }
}