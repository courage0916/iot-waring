package com.web.utils;

import com.web.model.dto.CustomUserDetails;
import com.web.model.vo.UserVO;
import com.web.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class UserUtil {

    private static UserService userService;


    @PostConstruct
    public void init(){
        userService = (UserService) BeanUtil.getBean(UserService.class);
    }


    public static UserVO getCurrUser(){
        CustomUserDetails customUserDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserVO user = userService.queryByName(customUserDetails.getUsername());
        user.setPassword("");
        return user;
    }



}
