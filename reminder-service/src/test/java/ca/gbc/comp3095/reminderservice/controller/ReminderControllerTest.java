package ca.gbc.comp3095.reminderservice.controller;

import ca.gbc.comp3095.reminderservice.dto.ReminderResponseDTO;
import ca.gbc.comp3095.reminderservice.service.ReminderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReminderController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class ReminderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReminderService reminderService;

    @Test
    @DisplayName("Test GET /api/v1/reminders returns list of reminders")
    void testGetAllReminders() throws Exception {
        // Use fixed timestamps as strings
        LocalDateTime dt1 = LocalDateTime.of(2026, 3, 31, 14, 0);
        LocalDateTime dt2 = LocalDateTime.of(2026, 4, 1, 14, 0);

        ReminderResponseDTO r1 = new ReminderResponseDTO(1L, 101L, "Finish assignment", dt1, false);
        ReminderResponseDTO r2 = new ReminderResponseDTO(2L, 102L, "Study for exam", dt2, false);

        List<ReminderResponseDTO> reminders = Arrays.asList(r1, r2);

        Mockito.when(reminderService.getAllReminders()).thenReturn(reminders);

        mockMvc.perform(get("/api/v1/reminders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].reminderId", is(1)))
                .andExpect(jsonPath("$[0].taskId", is(101)))
                .andExpect(jsonPath("$[0].message", is("Finish assignment")))
                .andExpect(jsonPath("$[0].scheduledAt", is("2026-03-31T14:00:00")))
                .andExpect(jsonPath("$[0].wasSent", is(false)))
                .andExpect(jsonPath("$[1].reminderId", is(2)))
                .andExpect(jsonPath("$[1].taskId", is(102)))
                .andExpect(jsonPath("$[1].message", is("Study for exam")))
                .andExpect(jsonPath("$[1].scheduledAt", is("2026-04-01T14:00:00")))
                .andExpect(jsonPath("$[1].wasSent", is(false)));
    }


    @Test
    @DisplayName("Test GET /api/v1/reminders returns empty list")
    void testGetRemindersEmpty() throws Exception {
        Mockito.when(reminderService.getAllReminders()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/v1/reminders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }


}