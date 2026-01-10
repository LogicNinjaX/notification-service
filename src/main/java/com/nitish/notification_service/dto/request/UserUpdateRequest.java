package com.nitish.notification_service.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest
        (
                String username,

                String email,

                String password
        ) { }
