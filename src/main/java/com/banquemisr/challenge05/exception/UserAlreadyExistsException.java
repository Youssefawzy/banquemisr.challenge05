package com.banquemisr.challenge05.exception;

public class UserAlreadyExistsException extends RuntimeException {
    private final int statusCode;

    public UserAlreadyExistsException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return  statusCode;
    }
}
