package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.ClientStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClientRegisterResponse
        (
                UUID clientId,

                String fullName,

                String email,

                ClientStatus status,

                LocalDateTime createdAt
        ) { }

