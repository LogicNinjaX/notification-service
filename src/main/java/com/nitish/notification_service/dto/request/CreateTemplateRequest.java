package com.nitish.notification_service.dto.request;

import com.nitish.notification_service.enums.ContentType;
import com.nitish.notification_service.enums.NotificationChannel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(
        name = "CreateTemplateRequest",
        description = "Request payload for creating a notification template"
)
public record CreateTemplateRequest
        (
                @NotBlank(message = "{template.name.not.blank}")
                @Size(max = 100, message = "{template.name.size}")
                @Schema(
                        description = "Template name",
                        example = "WELCOME_EMAIL",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                String name,

                @NotNull(message = "{template.channel.not-null}")
                @Schema(
                        description = "Notification channel for this template",
                        example = "EMAIL",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                NotificationChannel channel,

                @NotBlank(message = "{template.subject.not.blank}")
                @Size(max = 200, message = "{template.subject.size}")
                @Schema(
                        description = "Subject of the template (used in email/push)",
                        example = "Welcome to our platform",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                String subject,

                @NotBlank(message = "{template.content.not.blank}")
                @Size(max = 10000, message = "{template.content.size}")
                @Schema(
                        description = "Template body content. Can contain placeholders like {{name}}",
                        example = "Hello {{name}}, welcome to our platform!",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                String content,

                @NotNull(message = "{template.content-type.not-null}")
                @Schema(
                        description = "Content type of template",
                        example = "PLAIN_TEXT",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                ContentType contentType
        ) { }
