package com.nitish.notification_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginDetails
        (
                @NotBlank(message = "{user.username.not-blank}")
                String username,

                @NotBlank(message = "{user.email.not-blank}")
                String email,

                @NotBlank(message = "{user.password.not-blank}")
                @Length(min = 8, max = 15, message = "Password must be between {min} and {max} characters")
                String password
        ) {
}
