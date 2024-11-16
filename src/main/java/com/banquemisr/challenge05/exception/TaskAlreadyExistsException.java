package com.banquemisr.challenge05.exception;


public class TaskAlreadyExistsException extends RuntimeException {

    private final int statusCode;

    public TaskAlreadyExistsException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return  statusCode;
    }
}