package com.projects.hrproject.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projects.hrproject.dto.EmployeeCreateDto;
import com.projects.hrproject.dto.EmployeeResponseDto;
import com.projects.hrproject.dto.EmployeeUpdateDto;
import com.projects.hrproject.exception.EmailAlreadyUsedException;
import com.projects.hrproject.exception.EmployeeNotFoundException;
import com.projects.hrproject.mapper.EmployeeMapper;
import com.projects.hrproject.model.Employee;
import com.projects.hrproject.repository.EmployeeRepository;


@Service
public class EmployeeService {

    private final PasswordEncoder passwordEncoder;
	
	private final EmployeeRepository employeeRepository;
	private final EmployeeMapper employeeMapper;
	
    public EmployeeService(
    		EmployeeRepository employeeRepository,
    		EmployeeMapper employeeMapper,
    		PasswordEncoder passwordEncoder) {
    	
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
    }
	
	
	// Retrieve all employees from the database
    public List<EmployeeResponseDto> getEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponseDto)
                .toList();
    }
	
	// Retrieve a specific employee by their unique id
    public EmployeeResponseDto getEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        return employeeMapper.toResponseDto(employee);
    }
	
 
    // Create a new employee, ensuring the email is unique
    // The password is hashed using BCrypt
    public EmployeeResponseDto createEmployee(EmployeeCreateDto dto) {

        Employee employee = employeeMapper.toEntity(dto);

        if (employee.getLastName() != null) {
            employee.setLastName(employee.getLastName().trim().toUpperCase());
        }

        if (employee.getMail() != null) {
            employee.setMail(employee.getMail().trim().toLowerCase());
        }
        
        // Check if the email is already in use
        if (employeeRepository.existsByMail(employee.getMail())) {
            throw new EmailAlreadyUsedException(employee.getMail());
        }

        // Hash the user's password using BCrypt before persisting it
        employee.setPassword(passwordEncoder.encode(dto.password()));
        
        Employee savedEmployee = employeeRepository.save(employee);
        
        return employeeMapper.toResponseDto(savedEmployee);
    }
    
    
    // Update an existing employee
    public EmployeeResponseDto updateEmployee(
    		Long id, 
    		EmployeeUpdateDto dto) {

    	// Retrieve the employee or throw EmployeeNotFoundException
        Employee currentEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        
        employeeMapper.updateEntity(currentEmployee, dto);

        if (currentEmployee.getLastName() != null) {
            currentEmployee.setLastName(currentEmployee.getLastName().trim().toUpperCase());
        }

        if (currentEmployee.getMail() != null) {
            currentEmployee.setMail(currentEmployee.getMail().trim().toLowerCase());
        }
        
        // Check if the email is already used by another employee      
        if (employeeRepository.existsByMailAndIdNot(currentEmployee.getMail(), id)) {
            throw new EmailAlreadyUsedException(currentEmployee.getMail());
        }

        Employee updatedEmployee = employeeRepository.save(currentEmployee);
        
        return employeeMapper.toResponseDto(updatedEmployee);
    }


    public void deleteEmployee(Long id) {

        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }

        employeeRepository.deleteById(id);
    }
    
}