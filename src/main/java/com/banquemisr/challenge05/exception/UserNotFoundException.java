package com.banquemisr.challenge05.exception;

public class UserNotFoundException extends RuntimeException {
    private final int statusCode;

    public UserNotFoundException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
