package com.example.jobscheduler.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final Environment env;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, Environment env) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.env = env;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        boolean isDev = Arrays.asList(env.getActiveProfiles()).contains("dev");

        http
            .cors(cors -> {})
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(
                    "/",
                    "/index.html",
                    "/assets/**",
                    "/auth/login",
                    "/auth/register",
                    "/actuator/**",
                    "/login",
                    "/dashboard",
                    "/queues",
                    "/jobs",
                    "/workers",
                    "/dlq",
                    "/_redirect"
                ).permitAll();

                if (isDev) {
                    auth.anyRequest().permitAll();
                } else {
                    auth.anyRequest().authenticated();
                }
            })
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        if (!isDev) {
            http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        }

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:5173",
                "http://127.0.0.1:3000",
                "http://127.0.0.1:5173"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
