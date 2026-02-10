package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.NotificationChannel;
import com.nitish.notification_service.enums.RequestStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record NotificationRequestResponse
        (
                UUID requestId,
                NotificationChannel channel,
                RequestStatus status,
                LocalDateTime requestedAt,
                LocalDateTime updatedAt
        )
{ }
