package com.nitish.notification_service.exception.custom_exception;

import java.util.UUID;

public class EntityNotFoundException extends EntityException{

    private static final String message = "%s not found inside db [id=%s]";

    public EntityNotFoundException(String entityName, UUID id) {
        super(message.formatted(entityName, id));
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
