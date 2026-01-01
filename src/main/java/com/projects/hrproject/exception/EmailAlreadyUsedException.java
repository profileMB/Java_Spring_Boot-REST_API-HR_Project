package com.projects.hrproject.exception;

public class EmailAlreadyUsedException extends RuntimeException  {

    public EmailAlreadyUsedException(String email) {
        super("Email already used: " + email);
    }
	
}
