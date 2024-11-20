package com.personalfinance.personal_finance_app.service;

import com.personalfinance.personal_finance_app.model.entity.User;
import com.personalfinance.personal_finance_app.repository.RoleRepository;
import com.personalfinance.personal_finance_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;


    @Override
    public User registerUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public String verifyUser(User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );
            if(authentication.isAuthenticated()){
                return jwtService.generateToken(authentication);
            }
        } catch(BadCredentialsException e){
            return "";
        }
        return "";
    }
}
