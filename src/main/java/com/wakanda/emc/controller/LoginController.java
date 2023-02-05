package com.wakanda.emc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wakanda.emc.model.LoginRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
       // Validate the username and password
       if (validUsernameAndPassword(request.getUsername(), request.getPassword())) {
          return ResponseEntity.ok("Login Successful");
       } else {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
       }
    }
    
    private boolean validUsernameAndPassword(String username, String password) {
       // Implementation to check the username and password against a database or other data source
       return true;
    }
}

