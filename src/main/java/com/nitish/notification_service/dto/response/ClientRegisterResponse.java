package com.nitish.notification_service.dto.response;

import java.util.UUID;

public record ClientRegisterResponse
        (
                UUID clientId,
                UUID userId
        ) { }

