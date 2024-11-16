package com.banquemisr.challenge05.exception;

public class UserAuthenticationException extends RuntimeException {
    private final int statusCode;

    public UserAuthenticationException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}