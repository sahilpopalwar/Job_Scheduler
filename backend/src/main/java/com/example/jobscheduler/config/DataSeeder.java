package com.example.jobscheduler.config;

import com.example.jobscheduler.entity.User;
import com.example.jobscheduler.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User(
                    null,
                    "admin",
                    passwordEncoder.encode("admin123"),
                    "admin@jobscheduler.local",
                    "ROLE_ADMIN",
                    Instant.now()
            );
            userRepository.save(admin);
        }
    }
}
