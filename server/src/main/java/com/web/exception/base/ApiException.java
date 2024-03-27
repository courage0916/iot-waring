package com.web.exception.base;

public class ApiException extends RuntimeException {

    protected final String message;

    public ApiException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
