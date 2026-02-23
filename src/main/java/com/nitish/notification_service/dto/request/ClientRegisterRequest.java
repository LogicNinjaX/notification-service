package com.nitish.notification_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request payload for registering a new client")
public record ClientRegisterRequest
        (
                @NotNull(message = "Client details must be provided")
                @Valid
                @Schema(
                        description = "Complete client information",
                        implementation = ClientDetails.class,
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                ClientDetails clientDetails,


                @NotNull(message = "Login details must be provided")
                @Valid
                @Schema(
                        description = "Authentication and login information",
                        implementation = LoginDetails.class,
                        requiredMode = Schema.RequiredMode.REQUIRED
                )
                LoginDetails loginDetails
        ) {
}
