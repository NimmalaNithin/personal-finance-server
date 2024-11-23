package com.personalfinance.personal_finance_app.service;

import com.personalfinance.personal_finance_app.dto.UserLoginRequest;
import com.personalfinance.personal_finance_app.dto.UserRegisterRequest;
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
import org.springframework.web.bind.annotation.RequestBody;

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
    public String registerUser(UserRegisterRequest userRegisterRequest) {
        User user = new User();
        user.setUserName(userRegisterRequest.getUserName());
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterRequest.getPassword()));
        user.setFirstName(userRegisterRequest.getFirstName());
        user.setLastName(userRegisterRequest.getLastName());
        user.setEmail(userRegisterRequest.getEmail());
        user.setEnabled(true);
        user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserName(userRegisterRequest.getUserName());
        userLoginRequest.setPassword(userRegisterRequest.getPassword());
        return verifyUser(userLoginRequest);
    }

    @Override
    public String verifyUser(UserLoginRequest userLoginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequest.getUserName(), userLoginRequest.getPassword())
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
