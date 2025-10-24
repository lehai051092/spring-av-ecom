package com.ecommerce.project.security.services;

import jakarta.validation.Valid;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import com.ecommerce.project.security.dtos.LoginDTO;
import com.ecommerce.project.security.dtos.SignupDTO;
import com.ecommerce.project.security.response.LoginResponse;
import com.ecommerce.project.security.response.UserInfoResponse;

public interface AuthService {

    LoginResponse doLogin(LoginDTO loginDTO);

    ResponseEntity<?> registerUser(@Valid SignupDTO signupDTO);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    UserInfoResponse getUserProfile(UserDetailsImpl userDetails);

    ResponseCookie doLogout();
}
