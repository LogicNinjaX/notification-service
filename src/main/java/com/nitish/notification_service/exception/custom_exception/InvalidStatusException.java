package com.nitish.notification_service.exception.custom_exception;

public class InvalidStatusException extends StatusException{
    public InvalidStatusException(String message) {
        super(message);
    }
}
