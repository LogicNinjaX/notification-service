package com.nitish.notification_service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "{user.username.not-blank}")
        String username,

        @NotBlank(message = "{user.password.not-blank}")
        String password
) {
}
