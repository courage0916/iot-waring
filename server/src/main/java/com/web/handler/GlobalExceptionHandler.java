package com.web.handler;


import com.web.utils.Result;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Order(3)
public class GlobalExceptionHandler {


    @ExceptionHandler({AccessDeniedException.class})
    public Result accessDeniedException(AccessDeniedException e) {
        return Result.failure("没有权限");
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public Result IllegalArgumentException(IllegalArgumentException e) {
        return Result.failure(e.getMessage());
    }
}
