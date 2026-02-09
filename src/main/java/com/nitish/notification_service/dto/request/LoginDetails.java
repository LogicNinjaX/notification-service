package com.nitish.notification_service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginDetails
        (
                @NotBlank(message = "{user.username.not-blank}")
                String username,

                @NotBlank(message = "{user.email.not-blank}")
                String email,

                @NotBlank(message = "{user.password.not-blank}")
                String password
        ) {
}
