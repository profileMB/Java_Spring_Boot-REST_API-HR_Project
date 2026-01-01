package com.projects.hrproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projects.hrproject.dto.EmployeeCreateDto;
import com.projects.hrproject.dto.EmployeeResponseDto;
import com.projects.hrproject.dto.EmployeeUpdateDto;
import com.projects.hrproject.service.EmployeeService;
import com.projects.hrproject.task.DatabaseResetTask;

import jakarta.validation.Valid;

/**
 * Important!
 * This project is a public demo. To ensure the database remains "clean" and free from inappropriate changes,
 * this controller triggers an automatic database reset after write operations.
 * (See DatabaseResetTask in the task package for details)
 */

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final DatabaseResetTask databaseResetTask;

    public EmployeeController(
    		EmployeeService employeeService, 
    		DatabaseResetTask databaseResetTask) {
    	
        this.employeeService = employeeService;
        this.databaseResetTask = databaseResetTask;
    }
	
	
    /**
     * Get all employees.
     */
    @GetMapping
    public ResponseEntity<Iterable<EmployeeResponseDto>> getEmployees() {
        return ResponseEntity.ok(employeeService.getEmployees());
    }
	
    
    /**
     * Get one employee by ID.
     * If the employee does not exist, a 404 is returned automatically.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }
	
    
    /**
     * Create a new employee.
     * Returns HTTP 201 when the employee is saved.
     * Triggers a database reset schedule (demo constraint).
     */
    @PostMapping
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeCreateDto dto) {
    	
        databaseResetTask.scheduleReset();// Schedule DB reset in 1 hour (refer to the beginning of the class for details)
        return ResponseEntity
        		.status(201)
        		.body(employeeService.createEmployee(dto));
    }

	
    /**
     * Update an existing employee.
     */
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeResponseDto> updateEmployee(
			@PathVariable Long id, 
			@Valid @RequestBody EmployeeUpdateDto dto) {
		
	    databaseResetTask.scheduleReset();
	    return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
	}

	
    /**
     * Delete an employee by ID.
     * Returns 204 No Content if the delete succeeds.
     */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		databaseResetTask.scheduleReset();
		employeeService.deleteEmployee(id);
	    return ResponseEntity.noContent().build();
	}
	
}
