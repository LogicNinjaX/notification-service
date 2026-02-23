package com.nitish.notification_service.dto.request;

import com.nitish.notification_service.enums.NotificationChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(
        name = "UpdateTemplateRequest",
        description = "Request payload for updating template details (partial update allowed)"
)
public record UpdateTemplateRequest
        (
                @Size(max = 100, message = "{template.name.size}")
                @Schema(description = "Updated template name", example = "WELCOME_EMAIL")
                String name,

                @Schema(description = "Updated notification channel", example = "EMAIL")
                NotificationChannel channel,

                @Size(max = 200, message = "{template.subject.size}")
                @Schema(description = "Updated subject (mainly for email)", example = "Welcome!")
                String subject,

                @Size(max = 10000, message = "{template.content.size}")
                @Schema(description = "Updated template content", example = "Hello {{name}}")
                String content
        ) { }
