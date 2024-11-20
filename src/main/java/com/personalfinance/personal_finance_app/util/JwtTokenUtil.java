package com.personalfinance.personal_finance_app.util;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

@AllArgsConstructor
public class JwtTokenUtil {

    public static UUID getUserId(){
        String id = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return UUID.fromString(id);
    }
}
