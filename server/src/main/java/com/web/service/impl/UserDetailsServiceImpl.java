package com.web.service.impl;


import com.web.model.dto.CustomUserDetails;
import com.web.model.vo.UserVO;
import com.web.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Resource
    UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // todo redis 做布隆过滤器，过滤恶意访问

        UserVO user = userService.queryByName(username);

        Assert.isTrue(!ObjectUtils.isEmpty(user), "没有此用户的信息");

        return new CustomUserDetails(user);
    }
}
