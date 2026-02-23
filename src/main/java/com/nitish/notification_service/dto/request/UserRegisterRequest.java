package com.nitish.notification_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Schema(
        name = "UserRegisterRequest",
        description = "Request payload for registering a new user"
)
public record UserRegisterRequest
        (
                @NotNull(message = "{client.id.not-null}")
                @Schema(
                        description = "Client ID to which this user belongs",
                        example = "550e8400-e29b-41d4-a716-446655440000",
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                UUID clientId,

                @NotBlank(message = "{user.username.not-blank}")
                @Size(min = 3, max = 50, message = "{user.username.size}")
                @Schema(example = "nitish_sahni", requiredMode = Schema.RequiredMode.REQUIRED)
                String username,

                @NotBlank(message = "{user.email.not-blank}")
                @Email(message = "{user.email.format}")
                @Size(max = 150, message = "{user.email.size}")
                @Schema(example = "nitish@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
                String email,

                @NotBlank(message = "{user.password.not-blank}")
                @Size(min = 8, max = 20, message = "{user.password.size}")
                @Schema(
                        example = "StrongPass@123",
                        accessMode = Schema.AccessMode.WRITE_ONLY,
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                String password
        ) {
}
