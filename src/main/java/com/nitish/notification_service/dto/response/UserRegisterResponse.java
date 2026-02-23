package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response after successful user registration")
public record UserRegisterResponse
        (
                @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
                UUID userId,

                @Schema(example = "nitish_sahni")
                String username,

                @Schema(example = "nitish@gmail.com")
                String email,

                @Schema(example = "ROLE_USER")
                UserRole role,

                @Schema(example = "2026-02-23T21:10:00")
                LocalDateTime createdAt
        ) { }
