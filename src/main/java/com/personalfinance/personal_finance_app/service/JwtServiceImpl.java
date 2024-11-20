package com.personalfinance.personal_finance_app.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtServiceImpl implements JwtService{

    private String secretKey;

    public JwtServiceImpl() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
        SecretKey key = keyGenerator.generateKey();
        secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
    }

    private SecretKey getKey(){
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(Authentication authentication) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String userName = principal.getUsername();
        UUID userId = principal.getId();
        Collection<? extends GrantedAuthority> roles = principal.getAuthorities();
        List<String> roleList = roles.stream().map(GrantedAuthority::getAuthority).toList();

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId",userId);
        claims.put("role", roleList);

        return Jwts.builder()
                .claims()
                    .add(claims)
                    .subject(userName)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 ))
                .and()
                .signWith(getKey())
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String userNameFromToken = extractUserName(token);
        return(userNameFromToken.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = extractClaim(token,Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public UUID extractUserId(String token) {
        String id = extractAllClaims(token).get("userId").toString();
        UUID userId = UUID.fromString(id);
        return userId;
    }


    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
