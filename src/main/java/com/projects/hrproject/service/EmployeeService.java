package com.projects.hrproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.hrproject.model.Employee;
import com.projects.hrproject.repository.EmployeeRepository;


@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	
	// Retrieve all employees from the database
	public Iterable<Employee> getEmployees() {
		return employeeRepository.findAll();
	}
	
	
	// Retrieve a specific employee by their unique ID
	public Optional<Employee> getEmployee(final Long id) {
		return employeeRepository.findById(id);
	}
	
	
	// Save a new employee, ensuring the email is unique
    public Employee saveEmployee(Employee employee) {
    	
    	// Check if the email is already in use
        if (employeeRepository.existsByMail(employee.getMail())) {
            throw new RuntimeException("Employee creation failed: This email is already in use");
        }
        return employeeRepository.save(employee);
    }
    
    
    // Update an existing employee, ensuring the email is unique
    public Employee updateEmployee(Employee employee) {
    	
    	// Check if the email is already in use by another employee
        if ( employeeRepository.existsByMailAndIdNot(employee.getMail(), employee.getId())) { 
            throw new RuntimeException("Employee update failed: This email is already in use");
         }
        return employeeRepository.save(employee);
   }

    
    // Delete an employee by their ID
	public void deleteEmployee(final Long id) {
		employeeRepository.deleteById(id);
	}
	
}