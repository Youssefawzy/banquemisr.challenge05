package com.banquemisr.challenge05.exception;

public class HistoryNotFoundException extends RuntimeException {
    private final int statusCode;

    public HistoryNotFoundException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
