package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.DeliveryStatus;
import com.nitish.notification_service.enums.NotificationChannel;

import java.time.LocalDateTime;
import java.util.UUID;

public record DeliveryResponse
        (
                UUID deliveryId,
                NotificationChannel channel,
                DeliveryStatus status,
                int attemptNumber,
                String providerResponse,
                LocalDateTime sentAt,
                LocalDateTime updatedAt
        )
{ }
