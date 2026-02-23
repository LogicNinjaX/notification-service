package com.nitish.notification_service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Response after successful client registration")
public record ClientRegisterResponse
        (
                @Schema(description = "Generated client ID",
                        example = "550e8400-e29b-41d4-a716-446655440000")
                UUID clientId,

                @Schema(description = "Primary user ID created for the client",
                        example = "a1b2c3d4-e29b-41d4-a716-446655440000")
                UUID userId
        ) { }

