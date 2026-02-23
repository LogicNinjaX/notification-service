package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response after updating user details")
public record UserUpdateResponse
        (
                @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
                UUID userId,

                @Schema(example = "updated_username")
                String username,

                @Schema(example = "updated@email.com")
                String email,

                @Schema(example = "ROLE_USER")
                UserRole role,

                @Schema(example = "2026-02-23T20:00:00")
                LocalDateTime createdAt,

                @Schema(example = "2026-02-23T21:20:00")
                LocalDateTime updatedAt
        ) { }
