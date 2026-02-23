package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.NotificationChannel;
import com.nitish.notification_service.enums.RequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Notification request tracking details")
public record NotificationRequestResponse
        (
                @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
                UUID requestId,

                @Schema(example = "EMAIL")
                NotificationChannel channel,

                @Schema(example = "PROCESSING")
                RequestStatus status,

                @Schema(example = "2026-02-23T20:45:00")
                LocalDateTime requestedAt,

                @Schema(example = "2026-02-23T20:46:00")
                LocalDateTime updatedAt
        )
{ }
