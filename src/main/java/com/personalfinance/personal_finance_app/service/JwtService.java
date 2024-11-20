package com.personalfinance.personal_finance_app.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface JwtService {
    String generateToken(Authentication authentication);

    String extractUserName(String token);

    boolean validateToken(String token, UserDetails userDetails);

    UUID extractUserId(String token);
}
