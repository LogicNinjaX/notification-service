package com.nitish.notification_service.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Map;
import java.util.UUID;

public record SendNotificationRequest
        (
                //@NotBlank(message = "{template.id.not.blank}")
                UUID templateId,
                Map<String, Object> variables,
                String[] recipients
        ) { }
