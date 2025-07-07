package de.sonar.sonar.controllers;

import de.sonar.sonar.dto.JwtResponse;
import de.sonar.sonar.dto.LoginRequest;
import de.sonar.sonar.dto.RegisterRequest;
import de.sonar.sonar.model.entity.User;
import de.sonar.sonar.repositories.UserRepository;
import de.sonar.sonar.security.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AuthController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        User user;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedBirthDate = formatter.parse(request.getBirthDate());
            user = User.builder().email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).username(request.getUsername()).birthDate(parsedBirthDate).build();
            if (user == null) {
                return ResponseEntity.badRequest().body("E-Mail existiert bereits");
            }
            userRepo.save(user);
        } catch (ParseException e) {
            return ResponseEntity.status(500).body("Fehler be der Registrierung: " + e.getMessage());
        }

        return ResponseEntity.ok("Registrierung erfolgreich");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        log.info(request.toString());
        User user = userRepo.findByEmail(request.getEmail());
        if (user == null) {
            throw new RuntimeException("User nicht gefunden");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falsches Passwort");
        }

        String token = jwtUtils.generateToken(user.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
