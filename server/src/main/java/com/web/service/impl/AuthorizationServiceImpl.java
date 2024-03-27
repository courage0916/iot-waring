package com.web.service.impl;

import com.web.mapper.UserMapper;
import com.web.model.dto.CustomUserDetails;
import com.web.model.po.User;
import com.web.model.query.UserQuery;
import com.web.model.vo.UserVO;
import com.web.service.AuthorizationService;
import com.web.service.JwtService;
import com.web.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.web.mapper.UserDynamicSqlSupport.username;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {


    Map<String , Date> userStatus = new ConcurrentHashMap<>(16);

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    JwtService jwtService;



    @Resource
    UserDetailsService userDetailsService;


    @Resource
    UserService userService;

    @Resource
    UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String login(String username_, String password) {


        Authentication authentication = new UsernamePasswordAuthenticationToken(username_, password);

        Authentication authenticateResult = authenticationManager.authenticate(authentication);

        Assert.isTrue(authenticateResult != null, "用户密码错误");

        CustomUserDetails user = (CustomUserDetails) authenticateResult.getPrincipal();

        SecurityContextHolder.createEmptyContext().setAuthentication(authentication);


        User userVO = userMapper.selectOne(data -> data.
                where(username, isEqualTo(username_))
        ).get();


        Assert.isTrue(userVO.getLock() == 0 || (userVO.getLock() == 1 &&(!userStatus.containsKey(username_)  || ((new Date().getTime() - userStatus.get(username_).getTime())/1000) >= 60)), "账号已登录，需要退出才能重新登录");

        User user1 = new User();
        user1.setId(userVO.getId());
        user1.setLock((byte) 1);
        userMapper.updateByPrimaryKeySelective(user1);

        return jwtService.generateToken(user);
    }

    @Override
    public void loginOut(String token) {

        CustomUserDetails customUserDetails = analysisToken(token);

        User user1 = new User();
        user1.setId(userService.queryByName(customUserDetails.getUsername()).getId());
        user1.setLock((byte) 0);

        userMapper.updateByPrimaryKeySelective(user1);
    }

    @Override
    public void heartbeat() {

        userStatus.put(getCurrUser().getUsername(),new Date());

    }

    @Override
    public String refreshToken(String oldToken) {

        if (jwtService.isTokenExpired(oldToken)) {
            throw new RuntimeException("token 过期");
        }

        return jwtService.generateToken(userDetailsService.loadUserByUsername(jwtService.getUserName(oldToken)));
    }

    @Override
    public CustomUserDetails analysisToken(String token) {
        return (CustomUserDetails) userDetailsService.loadUserByUsername(jwtService.getUserName(token));
    }



    @Override
    public CustomUserDetails getCurrUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
