package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.NotificationChannel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response after template update")
public record UpdateTemplateResponse
        (
                UUID templateId,
                String name,
                NotificationChannel channel,
                String subject,
                String content,
                String placeholders,
                LocalDateTime updatedAt
        ) { }
