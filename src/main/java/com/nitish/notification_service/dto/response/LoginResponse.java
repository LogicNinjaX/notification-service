package com.nitish.notification_service.dto.response;

import com.nitish.notification_service.enums.AuthStatus;

public record LoginResponse(
        String token,
        AuthStatus authStatus
) { }
