package com.java.rest_api.config;

import com.java.rest_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList<>();
        List<com.java.rest_api.models.User> dbUsers = userService.findAllUsers();

        for (com.java.rest_api.models.User dbUser : dbUsers) {
            UserDetails userDetails = User.withUsername(dbUser.getUsername())
                    .password("{bcrypt}" + dbUser.getPassword())
                    .roles(dbUser.getRole())
                    .build();
            users.add(userDetails);
        }

        return new InMemoryUserDetailsManager(users);
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
