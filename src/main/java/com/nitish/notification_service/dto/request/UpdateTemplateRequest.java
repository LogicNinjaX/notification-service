package com.nitish.notification_service.dto.request;

import com.nitish.notification_service.enums.NotificationChannel;

public record UpdateTemplateRequest
        (
                String name,
                NotificationChannel channel,
                String subject,
                String content
        ) { }
