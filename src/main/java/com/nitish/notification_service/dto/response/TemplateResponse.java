package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.NotificationChannel;
import com.nitish.notification_service.enums.TemplateStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TemplateResponse
        (
                UUID templateId,

                String name,

                NotificationChannel channel,

                String subject,

                String content,

                String placeholders,

                TemplateStatus status,

                LocalDateTime createdAt,

                LocalDateTime updatedAt
        ) {
}
