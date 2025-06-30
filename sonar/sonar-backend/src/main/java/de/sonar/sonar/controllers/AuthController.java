package de.sonar.sonar.controllers;
import de.sonar.sonar.dto.*;
import de.sonar.sonar.model.User;
import de.sonar.sonar.repositories.UserRepository;
import de.sonar.sonar.security.JwtUtils;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        User user;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedBirthDate = formatter.parse(request.getBirthDate());
            user = User.builder().email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).username(request.getUsername()).birthDate(parsedBirthDate).build();
            if(user == null) {
                return ResponseEntity.badRequest().body("E-Mail existiert bereits");
            }
            userRepo.save(user);
        } catch(ParseException e) {
            //TODO Exception Handling
        }

        return ResponseEntity.ok("Registrierung erfolgreich");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail());
        if(user == null) {
            throw new RuntimeException("User nicht gefunden");
        }
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falsches Passwort");
        }

        String token = jwtUtils.generateToken(user.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
