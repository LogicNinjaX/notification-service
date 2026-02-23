package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.AuthStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response returned after successful authentication")
public record LoginResponse(

        @Schema(description = "JWT access token",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                accessMode = Schema.AccessMode.READ_ONLY)
        String token,

        @Schema(description = "Authentication status",
                example = "SUCCESS")
        AuthStatus authStatus
) { }
