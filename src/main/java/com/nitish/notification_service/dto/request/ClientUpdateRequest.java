package com.nitish.notification_service.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClientUpdateRequest
        (
                @NotBlank(message = "{client.name.not-blank}")
                String fullName,

                @NotBlank(message = "{client.email-not-blank}")
                @Email(message = "{client-email-format}")
                String email
        ) {
}
