package com.banquemisr.challenge05.exception;

public class NotificationNotFoundException extends RuntimeException {
    private final int statusCode;

    public NotificationNotFoundException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    public int getStatusCode() {
        return statusCode;
    }
}




