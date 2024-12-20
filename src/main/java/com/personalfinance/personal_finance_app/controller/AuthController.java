package com.personalfinance.personal_finance_app.controller;

import com.personalfinance.personal_finance_app.dto.TokenResponse;
import com.personalfinance.personal_finance_app.dto.UserLoginRequest;
import com.personalfinance.personal_finance_app.dto.UserRegisterRequest;
import com.personalfinance.personal_finance_app.model.entity.User;
import com.personalfinance.personal_finance_app.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        String token = authService.verifyUser(userLoginRequest);
        if(token.isEmpty()) {
            throw new RuntimeException("Invalid Username or password");
        }
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        String token = authService.registerUser(userRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TokenResponse(token));
    }
}
