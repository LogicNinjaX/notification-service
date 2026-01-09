package com.nitish.notification_service.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientUpdateResponse
        (
                UUID clientId,

                String fullName,

                String email,

                LocalDateTime updatedAt
        ) {
}
