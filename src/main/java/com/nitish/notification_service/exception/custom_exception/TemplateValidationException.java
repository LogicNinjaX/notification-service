package com.nitish.notification_service.exception.custom_exception;

public class TemplateValidationException extends RuntimeException {
    public TemplateValidationException(String message) {
        super(message);
    }
}
