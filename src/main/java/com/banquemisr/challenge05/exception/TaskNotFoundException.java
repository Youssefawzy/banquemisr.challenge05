package com.banquemisr.challenge05.exception;


public class TaskNotFoundException extends RuntimeException {
    private final int statusCode;

    public TaskNotFoundException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
