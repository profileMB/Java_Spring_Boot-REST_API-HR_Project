package com.projects.hrproject.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void existsByMail_shouldReturnTrue_whenMailExists() {
        boolean exists = employeeRepository.existsByMail("jeandupont@mail.com");
        assertTrue(exists);
    }

    @Test
    void existsByMail_shouldReturnFalse_whenMailDoesNotExist() {
        boolean exists = employeeRepository.existsByMail("unknown@mail.com");
        assertFalse(exists);
    }
}
