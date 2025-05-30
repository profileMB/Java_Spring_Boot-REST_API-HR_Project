package com.projects.hrproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projects.hrproject.model.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>{

	// Check if the email already exists before creation
	boolean existsByMail(String mail);
	
	// Check if the email is used by another employee before updating
	boolean existsByMailAndIdNot(String mail, Long id);
	
}