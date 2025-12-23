package com.java.rest_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        UserDetails applicant = User.withUsername("user")
                .password("{noop}user123")
                .roles("APPLICANT")
                .build();

        return new InMemoryUserDetailsManager(admin, applicant);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/customer/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/product/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/order/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/customer/**").hasAnyRole("ADMIN", "APPLICANT")
                        .requestMatchers(HttpMethod.GET, "/api/v1/product/**").hasAnyRole("ADMIN", "APPLICANT")
                        .requestMatchers(HttpMethod.GET, "/api/v1/order/**").hasAnyRole("ADMIN", "APPLICANT")
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").authenticated()
                        .anyRequest().hasRole("ADMIN")
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
