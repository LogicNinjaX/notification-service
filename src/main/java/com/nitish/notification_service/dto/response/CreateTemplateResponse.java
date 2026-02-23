package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.NotificationChannel;
import com.nitish.notification_service.enums.TemplateStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response after creating a notification template")
public record CreateTemplateResponse
        (
                @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
                UUID templateId,

                @Schema(example = "WELCOME_EMAIL")
                String name,

                @Schema(example = "EMAIL")
                NotificationChannel channel,

                @Schema(example = "ACTIVE")
                TemplateStatus status,

                @Schema(example = "2026-02-23T21:00:00")
                LocalDateTime createdAt
        ) { }
