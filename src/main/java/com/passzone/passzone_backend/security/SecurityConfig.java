package com.passzone.passzone_backend.security;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final FirebaseAuthFilter firebaseAuthFilter;

    public SecurityConfig(FirebaseAuthFilter firebaseAuthFilter) {
        this.firebaseAuthFilter = firebaseAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth

                // ✅ Autoriser CORS preflight
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // ✅ PUBLIC (fan)
                .requestMatchers(HttpMethod.GET, "/api/fanzones/**").permitAll()

                // ✅ routes publiques éventuelles
                .requestMatchers("/public/**").permitAll()

                // ✅ ADMIN protégé
                .requestMatchers("/api/admin/**").authenticated()

                // ✅ FAN PROTECTED (réservation + mes reservations)
                .requestMatchers("/api/reservations/**").authenticated()
                .requestMatchers("/api/my/**").authenticated()

                // ✅ tout le reste protégé
                .anyRequest().authenticated()
        );

        http.addFilterBefore(firebaseAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
