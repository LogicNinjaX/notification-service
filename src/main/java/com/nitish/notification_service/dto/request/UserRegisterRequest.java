package com.nitish.notification_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserRegisterRequest
        (
                @NotNull(message = "{client.id.not-null}")
                UUID clientId,

                @NotBlank(message = "{user.username.not-blank}")
                String username,

                @NotBlank(message = "{user.email.not-blank}")
                String email,

                @NotBlank(message = "{user.password.not-blank}")
                String password
        ) {
}
