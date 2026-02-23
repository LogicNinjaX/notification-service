package com.nitish.notification_service.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Map;
import java.util.UUID;

@Schema(
        name = "SendNotificationRequest",
        description = "Request payload to send notification using a template"
)
public record SendNotificationRequest
        (
                @NotNull(message = "{template.id.not.blank}")
                @Schema(
                        description = "Unique identifier of the template to use",
                        example = "550e8400-e29b-41d4-a716-446655440000",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                UUID templateId,

                @Schema(
                        description = "Dynamic variables used to replace placeholders in template (e.g. {{name}})",
                        example = "{\"name\":\"Nitish\",\"otp\":\"123456\"}"
                )
                Map<String, Object> variables,

                @Schema(
                        description = "List of recipient identifiers (email, phone number, etc.)",
                        example = "[\"nitish@gmail.com\", \"+919999999999\"]",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                String[] recipients
        ) { }
