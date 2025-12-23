package com.java.rest_api.controllers;

import com.java.rest_api.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    private JwtUtility jwtUtility;

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
