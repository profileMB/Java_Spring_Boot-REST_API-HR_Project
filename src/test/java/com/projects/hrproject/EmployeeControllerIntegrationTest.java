package com.projects.hrproject;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional 
public class EmployeeControllerIntegrationTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testGetEmployees() throws Exception {
		mockMvc.perform(get("/employees"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].firstName", is("Jean")))
		.andExpect(jsonPath("$[0].lastName", is("DUPONT")));
	}
	
    @Test
    public void testGetEmployeeById() throws Exception {
        mockMvc.perform(get("/employee/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.firstName", is("Jean")));
    }
    
    @Test
    public void testDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/employee/1"))
               .andExpect(status().isOk());
    }
	
}