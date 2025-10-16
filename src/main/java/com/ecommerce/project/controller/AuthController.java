package com.ecommerce.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.project.security.dtos.LoginDTO;
import com.ecommerce.project.security.response.UserInfoResponse;
import com.ecommerce.project.security.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {
        UserInfoResponse response = authService.doLogin(loginDTO);

        return ResponseEntity.ok(response);
    }
}
