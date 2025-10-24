package com.ecommerce.project.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.security.dtos.LoginDTO;
import com.ecommerce.project.security.dtos.SignupDTO;
import com.ecommerce.project.security.response.LoginResponse;
import com.ecommerce.project.security.response.MessageResponse;
import com.ecommerce.project.security.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {
        LoginResponse response = authService.doLogin(loginDTO);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, response.jwtCookie()).body(response.userInfoResponse());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDTO signupDTO) {
        if (authService.existsByUsername(signupDTO.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
        }

        if (authService.existsByEmail(signupDTO.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
        }

        return authService.registerUser(signupDTO);
    }
}
