package de.sonar.sonar.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
