package com.projects.hrproject.mapper;

import org.springframework.stereotype.Component;

import com.projects.hrproject.dto.EmployeeCreateDto;
import com.projects.hrproject.dto.EmployeeResponseDto;
import com.projects.hrproject.dto.EmployeeUpdateDto;
import com.projects.hrproject.model.Employee;

@Component
public class EmployeeMapper {
	
    public EmployeeResponseDto toResponseDto(Employee employee) {
    	
        return new EmployeeResponseDto(
        		
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getMail()
        );
    }

    public Employee toEntity(EmployeeCreateDto dto) {
    	
        Employee employee = new Employee();
        
        employee.setFirstName(dto.firstName());
        employee.setLastName(dto.lastName());
        employee.setMail(dto.mail());
        employee.setPassword(dto.password());
        
        return employee;
    }

    public void updateEntity(
    		Employee employee, 
    		EmployeeUpdateDto dto) {
    	
        employee.setFirstName(dto.firstName());
        employee.setLastName(dto.lastName());
        employee.setMail(dto.mail());
    }

}
