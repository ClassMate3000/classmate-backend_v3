package com.classmate.userservice.controller;

import com.classmate.userservice.dto.AuthResponseDTO;
import com.classmate.userservice.dto.LoginRequestDTO;
import com.classmate.userservice.dto.RegisterRequestDTO;
import com.classmate.userservice.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) // disables Spring Security for tests
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterSuccess() throws Exception {

        // Arrange
        RegisterRequestDTO request = new RegisterRequestDTO(
                "John", "Doe", "john@example.com", "password123"
        );

        AuthResponseDTO response = new AuthResponseDTO(1L, "mock-jwt-token", "Test", "User");

        when(authService.register(request)).thenReturn(response);

        // Act + Assert
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
    }

    @Test
    void testLoginSuccess() throws Exception {

        // Arrange
        LoginRequestDTO request = new LoginRequestDTO(
                "john@example.com", "password123"
        );

        AuthResponseDTO response = new AuthResponseDTO(1L, "mock-jwt-token", "Test", "User");

        when(authService.login(request)).thenReturn(response);

        // Act + Assert
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.token").value("mock-jwt-token"));
    }


}