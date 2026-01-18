package com.nitish.notification_service.dto.request;

import java.util.Map;
import java.util.UUID;

public record SendNotificationRequest
        (
                UUID templateId,
                Map<String, Object> variables,
                String[] recipients
        ) { }
