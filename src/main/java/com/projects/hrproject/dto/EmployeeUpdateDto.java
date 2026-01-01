package com.projects.hrproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmployeeUpdateDto(
		
        @NotBlank(message = "Le prénom est obligatoire")
        @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
        String firstName,

        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
        String lastName,

        @Email(message = "Adresse e-mail invalide")
        @NotBlank(message = "L'e-mail est obligatoire")
        @Size(max = 100, message = "L'e-mail ne doit pas dépasser 100 caractères")
        String mail

) {}
