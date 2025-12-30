package com.java.rest_api.controllers;

import com.java.rest_api.utility.JwtUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Login Management", description = "APIs for managing logins")
public class LoginController {

    @Autowired
    private JwtUtility jwtUtility;

    @Operation(summary = "Attempt to login", description = "Attempt to login by retrieving a valid JWT")
    @PostMapping("/login")
    public Map<String, String> login(Principal principal) {
        // Generate token
        String token = jwtUtility.generateToken(principal.getName());

        // Return as JSON
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return response;
    }
}
