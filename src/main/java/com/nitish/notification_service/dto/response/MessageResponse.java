package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.MessageStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageResponse
        (
                UUID messageId,
                String recipient,
                String renderedSubject,
                String renderedContent,
                MessageStatus status,
                int retryCount,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        )
{ }
