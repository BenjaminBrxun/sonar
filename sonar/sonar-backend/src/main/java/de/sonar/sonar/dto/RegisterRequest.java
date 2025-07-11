package de.sonar.sonar.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String username;
    private String birthDate; // z.B. als String oder LocalDate
}