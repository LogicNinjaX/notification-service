package com.nitish.notification_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(
        name = "LoginDetails",
        description = "User registration details"
)
public record LoginDetails
        (
                @NotBlank(message = "{user.username.not-blank}")
                @Size(min = 3, max = 50, message = "{user.username.size}")
                @Schema(
                        description = "Unique username",
                        example = "nitish_sahni",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                String username,

                @NotBlank(message = "{user.email.not-blank}")
                @Email(message = "{user.email.format}")
                @Size(max = 150, message = "{user.email.size}")
                @Schema(
                        description = "User email address",
                        example = "nitish@gmail.com",
                        format = "email",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                String email,

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
