package com.example.jobscheduler.controller;

import com.example.jobscheduler.entity.User;
import com.example.jobscheduler.repository.UserRepository;
import com.example.jobscheduler.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, JwtUtil jwtUtil, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String email = body.get("email");

        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("username taken");
        }

        User u = new User(null, username, passwordEncoder.encode(password), email, "ROLE_USER", Instant.now());
        userRepository.save(u);
        return ResponseEntity.ok(Map.of("id", u.getId()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        var userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("invalid credentials");
        }
        var user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body("invalid credentials");
        }

        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
