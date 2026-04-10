package com.classmate.userservice.controller;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProtectedController.class)
@AutoConfigureMockMvc(addFilters = false) // disable security
@ActiveProfiles("test")
public class ProtectedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHelloProtected() throws Exception {

        mockMvc.perform(get("/api/protected/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello! You have accessed a protected endpoint."));
    }
}