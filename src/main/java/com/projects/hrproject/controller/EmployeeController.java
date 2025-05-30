package com.projects.hrproject.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.projects.hrproject.DatabaseResetTask;
import com.projects.hrproject.model.Employee;
import com.projects.hrproject.service.EmployeeService;

/**
 * Important!!
 * This project is a public demo. To ensure that the database remains "clean" (i.e. without inappropriate modifications), 
 * a reset mechanism is implemented using the DatabaseResetTask class. 
 * Every time an employee is created, updated, or deleted, the database reset task is scheduled to run after 1 hour,
 * restoring the database to its initial state.
 * (See the DatabaseResetTask class in the root package for details)
 */

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
    private DatabaseResetTask databaseResetTask;
	
	
	/**
     * Retrieve all employees
     * 
     * @return Iterable list of all employees
     */
	@GetMapping("/employees")
	public Iterable<Employee> getEmployees() {
		return employeeService.getEmployees();	
	}
	
	
	/**
     * Retrieve an employee by their unique ID
     * 
     * @param id The unique identifier of the employee
     * @return Employee object if found, otherwise null
     */
	@GetMapping("/employee/{id}")
	public Employee getEmployee(@PathVariable final Long id) {
		Optional<Employee> employee = employeeService.getEmployee(id);
		return employee.orElse(null);
	}
	
	
    /**
     * Create a new employee
     * 
     * @param employee The employee object to be created
     * @return The saved employee
     */
	@PostMapping("/employee")
	public Employee createEmployee(@RequestBody Employee employee) {
		databaseResetTask.scheduleReset();// Schedule DB reset in 1 hour (refer to the beginning of the class for details)
		return employeeService.saveEmployee(employee);
	}

	
    /**
     * Update an existing employee
     * Updates only non-null fields provided in the request
     * 
     * @param id The ID of the employee to update
     * @param employee The employee object containing the updated data
     * @return The updated employee if found, otherwise null
     */
	@PutMapping("/employee/{id}")
	public Employee updateEmployee(@PathVariable final Long id, @RequestBody Employee employee) {
		
		databaseResetTask.scheduleReset();  //Schedule DB reset in 1 hour (refer to the beginning of the class for details)
		
		Optional<Employee> employeeOpt= employeeService.getEmployee(id);
		if(employeeOpt.isPresent()) {
			
			// If the employee exists, get the current employee data
			Employee currentEmployee = employeeOpt.get();
			
			// Update the first name if it is provided (non-null)
			String firstName = employee.getFirstName();
			if(firstName != null) {
				currentEmployee.setFirstName(firstName);
			}
			
			String lastName = employee.getLastName();
			if(lastName != null) {
				currentEmployee.setLastName(lastName);
			}
			
			String mail = employee.getMail();
			if(mail != null) {
				currentEmployee.setMail(mail);
			}
			
		    // Save the updated employee object
			employeeService.updateEmployee(currentEmployee);
			return currentEmployee;
			
		} else {
			
			return null;
		}
		
	}
	
	
    /**
     * Delete an employee by their unique ID
     * 
     * @param id The ID of the employee to delete
     */
	@DeleteMapping("/employee/{id}")
	public void deleteEmployee(@PathVariable final Long id) {
		databaseResetTask.scheduleReset();// Schedule DB reset in 1 hour (refer to the beginning of the class for details)
		employeeService.deleteEmployee(id);
	}
	
}
