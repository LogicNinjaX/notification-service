package com.nitish.notification_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response after updating client details")
public record ClientUpdateResponse
        (
                @Schema(example = "550e8400-e29b-41d4-a716-446655440000")
                UUID clientId,

                @Schema(example = "Updated Name")
                String fullName,

                @Schema(example = "updated@email.com")
                String email,

                @Schema(example = "2026-02-23T21:20:00")
                LocalDateTime updatedAt
        ) {
}
