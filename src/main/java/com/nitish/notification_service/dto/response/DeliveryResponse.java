package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.DeliveryStatus;
import com.nitish.notification_service.enums.NotificationChannel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Notification delivery details")
public record DeliveryResponse
        (
                @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
                UUID deliveryId,

                @Schema(example = "EMAIL")
                NotificationChannel channel,

                @Schema(example = "SUCCESS")
                DeliveryStatus status,

                @Schema(description = "Number of attempts made to deliver the message",
                        example = "1")
                int attemptNumber,

                @Schema(description = "Provider response message (if any)",
                        example = "Message accepted by provider")
                String providerResponse,

                @Schema(example = "2026-02-23T21:10:00")
                LocalDateTime sentAt,

                @Schema(example = "2026-02-23T21:12:00")
                LocalDateTime updatedAt
        )
{ }
