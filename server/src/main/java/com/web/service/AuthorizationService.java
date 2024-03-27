package com.web.service;


import com.web.model.dto.CustomUserDetails;
import jakarta.servlet.http.HttpSession;

public interface AuthorizationService {

    String login(String username, String password);

    void loginOut(String token);

    void heartbeat();

    String refreshToken(String oldToken);

    CustomUserDetails analysisToken(String token);


    CustomUserDetails getCurrUser();
}
