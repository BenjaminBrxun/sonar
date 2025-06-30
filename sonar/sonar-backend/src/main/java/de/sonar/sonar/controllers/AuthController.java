package de.sonar.sonar.controllers;
import de.sonar.sonar.dto.JwtResponse;
import de.sonar.sonar.dto.LoginRequest;
import de.sonar.sonar.model.User;
import de.sonar.sonar.repositories.UserRepository;
import de.sonar.sonar.security.JwtUtils;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if(userRepo.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("E-Mail existiert bereits");
        }
        User user = User.builder().email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).username(request.getUsername()).birthDate(request.getBirthDate()).build();

        userRepo.save(user);
        return ResponseEntity.ok("Registrierung erfolgreich");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User nicht gefunden"));
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falsches Passwort");
        }

        String token = jwtUtils.generateToken(user.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
