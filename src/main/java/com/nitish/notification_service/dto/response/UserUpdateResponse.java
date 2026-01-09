package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserUpdateResponse
        (
                UUID userId,

                String username,

                String email,

                String password,

                UserRole role,

                LocalDateTime createdAt,

                LocalDateTime updatedAt
        ) { }
