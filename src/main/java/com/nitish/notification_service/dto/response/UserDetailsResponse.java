package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDetailsResponse
        (
                UUID userId,

                String username,

                String email,

                UserRole role,

                LocalDateTime createdAt,

                LocalDateTime updatedAt

        ) {
}
