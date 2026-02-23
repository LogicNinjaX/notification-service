package com.nitish.notification_service.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Schema(
        name = "UserUpdateRequest",
        description = "Request payload for updating user details (partial update allowed)"
)
public record UserUpdateRequest
        (
                @Size(min = 3, max = 50, message = "{user.username.size}")
                @Schema(example = "new_username")
                String username,

                @Email(message = "{user.email.format}")
                @Size(max = 150, message = "{user.email.size}")
                @Schema(example = "new@gmail.com")
                String email,

                @Size(min = 8, max = 20, message = "{user.password.size}")
                @Schema(
                        example = "NewStrongPass@123",
                        accessMode = Schema.AccessMode.WRITE_ONLY
                )
                String password
        ) { }
