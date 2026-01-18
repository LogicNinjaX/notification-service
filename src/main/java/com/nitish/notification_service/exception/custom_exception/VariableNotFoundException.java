package com.nitish.notification_service.exception.custom_exception;

public class VariableNotFoundException extends RuntimeException{
    public VariableNotFoundException(String message) {
        super(message);
    }
}
