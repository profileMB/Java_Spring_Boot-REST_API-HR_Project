package com.projects.hrproject;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.projects.hrproject.controller.EmployeeController;
import com.projects.hrproject.service.EmployeeService;

@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeControllerUnitTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private EmployeeService employeeService;
	
	@MockBean
    private DatabaseResetTask databaseResetTask;
	
	@Test
	public void testGetEmployees() throws Exception {
		mockMvc.perform(get("/employees"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testGetEmployee() throws Exception {
		mockMvc.perform(get("/employee/1"))
			.andExpect(status().isOk());
	}

}