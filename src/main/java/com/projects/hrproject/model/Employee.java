package com.projects.hrproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")// Max size constraint for data integrity and to avoid large payloads impacting the API or DB.
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Adresse e-mail invalide")
    @NotBlank(message = "L'e-mail est obligatoire")
    @Size(max = 100, message = "L'e-mail ne doit pas dépasser 100 caractères")
    @Column(unique = true)
    private String mail;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, max = 60)
    private String password;
	
}