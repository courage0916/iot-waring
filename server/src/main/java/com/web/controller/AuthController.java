package com.web.controller;

import com.web.model.dto.CustomUserDetails;
import com.web.service.AuthorizationService;
import com.web.utils.Result;
import jakarta.annotation.Resource;
import javax.validation.Valid;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Resource
    AuthorizationService authorizationService;


    @PostMapping("/login")
    public Result<String> login(String username, String password) {
        return Result.success("通行证获取成功", authorizationService.login(username, password));
    }

    @PostMapping("/login/out")
    public void loginOut(String token) {
        authorizationService.loginOut(token);
    }

    @PostMapping("/heartbeat")
    public void heartbeat() {
        authorizationService.heartbeat();
    }

    @PostMapping("/refresh/token")
    public Result<String> refreshToken(String token) {
        return Result.success("通行证刷新成功", authorizationService.refreshToken(token));
    }

    @PostMapping("/analysis/token")
    public CustomUserDetails analysisToken(@Valid String token) {
        return authorizationService.analysisToken(token);
    }

}
