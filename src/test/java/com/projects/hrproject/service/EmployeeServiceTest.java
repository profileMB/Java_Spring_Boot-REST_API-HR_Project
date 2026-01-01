package com.projects.hrproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.projects.hrproject.dto.EmployeeCreateDto;
import com.projects.hrproject.dto.EmployeeResponseDto;
import com.projects.hrproject.exception.EmailAlreadyUsedException;
import com.projects.hrproject.mapper.EmployeeMapper;
import com.projects.hrproject.model.Employee;
import com.projects.hrproject.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void createEmployee_shouldCreateEmployeeSuccessfully() {

        EmployeeCreateDto dto = new EmployeeCreateDto(
                "Jean",
                "Dupont",
                "Jean.Dupont@MAIL.COM",
                "password123"
        );

        Employee employee = new Employee();
        employee.setMail("jean.dupont@mail.com");

        when(employeeMapper.toEntity(dto)).thenReturn(employee);
        when(employeeRepository.existsByMail("jean.dupont@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hashed");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        when(employeeMapper.toResponseDto(employee))
                .thenReturn(new EmployeeResponseDto(
                        1L,
                        "Jean",
                        "DUPONT",
                        "jean.dupont@mail.com"
                ));

 
        EmployeeResponseDto result = employeeService.createEmployee(dto);


        assertNotNull(result);
        assertEquals("jean.dupont@mail.com", result.mail());
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void createEmployee_shouldThrowException_whenEmailAlreadyUsed() {

        EmployeeCreateDto dto = new EmployeeCreateDto(
                "Jean",
                "Dupont",
                "test@mail.com",
                "password"
        );

        Employee employee = new Employee();
        employee.setMail("test@mail.com");

        when(employeeMapper.toEntity(dto)).thenReturn(employee);
        when(employeeRepository.existsByMail("test@mail.com")).thenReturn(true);


        assertThrows(
                EmailAlreadyUsedException.class,
                () -> employeeService.createEmployee(dto)
        );
    }
}
