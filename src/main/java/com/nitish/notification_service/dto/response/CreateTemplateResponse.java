package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.NotificationChannel;
import com.nitish.notification_service.enums.TemplateStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateTemplateResponse
        (
                UUID templateId,
                String name,
                NotificationChannel channel,
                TemplateStatus status,
                LocalDateTime createdAt
        ) { }
