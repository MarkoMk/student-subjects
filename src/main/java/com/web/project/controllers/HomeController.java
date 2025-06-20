package com.web.project.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class HomeController {

    private final RestTemplate restTemplate;

    // User class for sending/receiving user data via REST API
    private static class User {
        String username;
        String password;
        String name;
        String surname;
        String dob;
        String email;

        // Constructor for creating user
        public User(String username, String password, String name, String surname, String dob, String email) {
            this.username = username;
            this.password = password;
            this.name = name;
            this.surname = surname;
            this.dob = dob;
            this.email = email;
        }

        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getSurname() { return surname; }
        public void setSurname(String surname) { this.surname = surname; }
        public String getDob() { return dob; }
        public void setDob(String dob) { this.dob = dob; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    // Render home page
    @GetMapping(value = {"/home", "/", ""})
    public String viewHomePage() {
        return "home";
    }

    // Login endpoint
    @PostMapping("/api/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        Map<String, String> response = new HashMap<>();

        // Fetch user from REST API
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> getUserEntity = new HttpEntity<>(headers);
        try {
            ResponseEntity<User> userResponse = restTemplate.exchange(
                    "http://localhost:8080/api/users/" + username,
                    HttpMethod.GET,
                    getUserEntity,
                    User.class
            );
            User user = userResponse.getBody();

            // Verify password (plain string comparison)
            if (user != null && user.getPassword().equals(password)) {
                response.put("message", "Login successful");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "Invalid username or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // Signup endpoint
    @PostMapping("/api/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody Map<String, String> signupData) {
        String name = signupData.get("name");
        String surname = signupData.get("surname");
        String dob = signupData.get("dob");
        String email = signupData.get("email");
        String password = signupData.get("password");
        String confirmPassword = signupData.get("confirmPassword");
        String username = signupData.get("email"); // Using email as username

        Map<String, String> response = new HashMap<>();

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            response.put("message", "Passwords do not match");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Check if user already exists
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> getUserEntity = new HttpEntity<>(headers);
        try {
            restTemplate.exchange(
                    "http://localhost:8080/api/users/" + username,
                    HttpMethod.GET,
                    getUserEntity,
                    User.class
            );
            response.put("message", "User already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            // User does not exist, proceed with signup
        }

        // Create user with plain password
        User user = new User(username, password, name, surname, dob, email);

        // Send user to REST API
        HttpEntity<User> createUserEntity = new HttpEntity<>(user, headers);
        try {
            restTemplate.exchange(
                    "http://localhost:8080/api/users",
                    HttpMethod.POST,
                    createUserEntity,
                    User.class
            );
            response.put("message", "Signup successful");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Error during signup: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Google signup endpoint (placeholder)
    @PostMapping("/api/google-signup")
    public ResponseEntity<Map<String, String>> googleSignup(@RequestBody Map<String, String> googleData) {
        Map<String, String> response = new HashMap<>();
        // Note: Actual Google OAuth requires validating an ID token from Google's OAuth flow
        response.put("message", "Google signup not fully implemented. Requires OAuth setup.");
        return ResponseEntity.ok(response);
    }
}