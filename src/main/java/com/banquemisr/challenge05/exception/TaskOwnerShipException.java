package com.banquemisr.challenge05.exception;

public class TaskOwnerShipException extends  RuntimeException{
    private final int statusCode;

    public TaskOwnerShipException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return  statusCode;
    }
}
