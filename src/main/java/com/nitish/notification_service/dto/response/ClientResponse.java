package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.ClientStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Client details response")
public record ClientResponse(

        @Schema(description = "Client ID",
                example = "550e8400-e29b-41d4-a716-446655440000")
        UUID clientId,

        @Schema(description = "Client full name",
                example = "XYZ Pvt Ltd")
        String fullName,

        @Schema(description = "Client email",
                example = "contact@acme.com")
        String email,

        @Schema(description = "Client status",
                example = "ACTIVE")
        ClientStatus status,

        @Schema(description = "Client creation timestamp",
                example = "2026-02-23T20:15:30")
        LocalDateTime createdAt,

        @Schema(description = "Last updated timestamp",
                example = "2026-02-23T21:10:00")
        LocalDateTime updatedAt
) { }
