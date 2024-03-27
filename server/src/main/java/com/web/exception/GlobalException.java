package com.web.exception;


import com.web.exception.base.ApiException;
import com.web.utils.Result;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Order(6)
@Slf4j
public class GlobalException {

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result<String> handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage());
        return Result.failure("不支持' " + e.getMethod() + "'请求");
    }

    @ExceptionHandler({UncategorizedSQLException.class})
    public Result<String> notFount(UncategorizedSQLException e) {
        log.error(e.getMessage());
        return Result.failure("SQL 异常:" + e.getCause().getMessage());
    }

    public Result<String> notFount(RuntimeException e) {
        log.error(e.getMessage());
        if(StringUtils.isEmpty(e.getMessage())){
            return Result.failure("运行时异常:" + e.getCause().getMessage());
        }
        return Result.failure("运行时异常:" + e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public Result<String> authenticationException(AuthenticationException e) {
        log.error(e.getMessage());
        return Result.failure("密码错误");
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error(e.getMessage());
        return Result.failure("服务器错误，请联系管理员");
    }

    @ExceptionHandler(ApiException.class)
    public Result<String> businessException(HttpServletRequest request, ApiException e) {
        log.error(e.getMessage());
        return Result.failure(e.getMessage());
    }


}
