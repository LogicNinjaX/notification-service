package com.nitish.notification_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "LoginRequest",
        description = "Request payload for user authentication"
)
public record LoginRequest(

        @NotBlank(message = "{user.username.not-blank}")
        @Size(max = 50, message = "{user.username.size}")
        @Schema(
                description = "Username of the user",
                example = "nitish_sahni",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String username,

        @NotBlank(message = "{user.password.not-blank}")
        @Size(min = 8, max = 20, message = "{user.password.size}")
        @Schema(
                description = "User password",
                example = "StrongPass@123",
                accessMode = Schema.AccessMode.WRITE_ONLY,
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String password
) {
}
