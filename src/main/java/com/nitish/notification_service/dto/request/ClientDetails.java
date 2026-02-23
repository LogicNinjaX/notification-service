package com.nitish.notification_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "ClientDetails",
        description = "Client registration details"
)
public record ClientDetails
        (
                @NotBlank(message = "{client.name.not-blank}")
                @Size(min = 5, max = 100, message = "{client.name.size}")
                @Schema(
                        description = "Full name of the client or company",
                        example = "Nitish Sahni",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                String fullName,

                @Email(message = "{client.email.format}")
                @Size(max = 150, message = "{client.email.size}")
                @Schema(
                        description = "Valid email address of the client",
                        example = "nitish@gmail.com",
                        format = "email",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                String email
        ) {
}
