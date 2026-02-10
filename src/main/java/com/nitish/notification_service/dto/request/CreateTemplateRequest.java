package com.nitish.notification_service.dto.request;

import com.nitish.notification_service.enums.ContentType;
import com.nitish.notification_service.enums.NotificationChannel;
import jakarta.validation.constraints.NotBlank;

public record CreateTemplateRequest
        (
                @NotBlank(message = "{template.name.not.blank}")
                String name,

                NotificationChannel channel,

                @NotBlank(message = "{template.subject.not.blank}")
                String subject,

                @NotBlank(message = "{template.content.not.blank}")
                String content,

                ContentType contentType
        ) { }
