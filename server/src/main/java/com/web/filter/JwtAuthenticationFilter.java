package com.web.filter;



import com.web.handler.MyAuthenticationFailureHandler;
import com.web.service.JwtService;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    JwtService jwtService;

    @Resource
    UserDetailsService userDetailsService;

    @Resource
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        String userName = null;
   /*     filterChain.doFilter(request, response);
        return;*/
        try {

            if (request.getRequestURI().contains("/auth/login")){
                filterChain.doFilter(request, response);
                return;
            }

            if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "")) {
                if (request.getRequestURI().contains("/auth/")){
                    filterChain.doFilter(request, response);
                    return;
                }

               throw new AccountExpiredException("没有通行证");
            }
            jwt = authHeader.substring(7);
            userName = jwtService.extractUserName(jwt);
            if (StringUtils.isEmpty(userName)
                    //todo 加个单例线程获取
                    /*|| SecurityContextHolder.getContext().getAuthentication() == null*/) {
                throw new AccountExpiredException("通行证不合法");
            }
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
                filterChain.doFilter(request, response);
            }
        }catch (Exception e){
            myAuthenticationFailureHandler.onAuthenticationFailure(request,response,new AccountExpiredException(e.getMessage()));
        }

    }
}
