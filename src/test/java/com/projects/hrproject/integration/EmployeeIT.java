package com.projects.hrproject.integration;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class EmployeeIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void fullScenario_createAndRetrieveEmployee() throws Exception {

        String createJson = """
            {
              "firstName": "Luc",
              "lastName": "Durand",
              "mail": "luc.durand@mail.com",
              "password": "password123"
            }
            """;

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mail").value("luc.durand@mail.com"));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].mail")
                        .value(hasItem("luc.durand@mail.com")));
    }

    @Test
    void createEmployee_shouldReturn409_whenEmailAlreadyExists() throws Exception {

        String json = """
            {
              "firstName": "Jean",
              "lastName": "Dupont",
              "mail": "jeandupont@mail.com",
              "password": "password123"
            }
            """;

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict());
    }
}