package com.web.service;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token) throws AccountExpiredException;

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    String getUserName(String token);
}