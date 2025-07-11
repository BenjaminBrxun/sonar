package de.sonar.sonar.controllers;

import de.sonar.sonar.dto.JwtResponse;
import de.sonar.sonar.dto.LoginRequest;
import de.sonar.sonar.dto.RegisterRequest;
import de.sonar.sonar.model.entity.User;
import de.sonar.sonar.security.JwtUtils;
import de.sonar.sonar.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
@Log4j2
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    @Operation(
            summary = "Register a new user.",
            tags = "Auth")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        User user;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedBirthDate = formatter.parse(request.getBirthDate());
            user = User.builder().email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).username(request.getUsername()).birthDate(parsedBirthDate).build();
            log.info(user.getPassword());
            userService.registerUser(user);
        } catch (ParseException e) {
            return ResponseEntity.status(500).body("Fehler bei der Registrierung: " + e.getMessage());
        }
        return ResponseEntity.ok("Registrierung erfolgreich");
    }

    @Operation(
            summary = "Login with username and password.",
            tags = "Auth")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.info(request.toString());
        User user = userService.getUserByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falsche E-Mail");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falsches Passwort");
        }

        String token = jwtUtils.generateToken(user.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
