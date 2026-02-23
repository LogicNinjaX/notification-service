package com.nitish.notification_service.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Schema(
        name = "ClientUpdateRequest",
        description = "Request payload for updating existing client details"
)
public record ClientUpdateRequest
        (
                @Size(min = 2, max = 100, message = "{client.name.size}")
                @Schema(
                        description = "Updated full name of the client",
                        example = "Nitish Sahni",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                String fullName,

                @Email(message = "{client-email-format}")
                @Size(max = 150, message = "{client.email.size}")
                @Schema(
                        description = "Updated email address of the client",
                        example = "nitish@gmail.com",
                        format = "email",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                String email
        ) {
}
