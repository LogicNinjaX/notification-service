package com.nitish.notification_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "client registration schema")
public record ClientDetails
        (
                @Schema(description = "client name", example = "company name/your name")
                @NotBlank(message = "{client.name.not-blank}")
                String fullName,

                @Schema(description = "client email", example = "xyz@gmail.com/xyz.@hotmail.com/xyz@yahoo.com")
                @NotBlank(message = "{client.email-not-blank}")
                @Email(message = "{client-email-format}")
                String email
        ) {
}
