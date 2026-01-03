package com.nitish.notification_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "client registration schema")
public class ClientRegisterRequest {

    @Schema(description = "client name", example = "company name/your name")
    @NotBlank(message = "{client.name.not-blank}")
    private String fullName;

    @Schema(description = "client email", example = "xyz@gmail.com/xyz.@hotmail.com/xyz@yahoo.com")
    @NotBlank(message = "{client.email-not-blank}")
    @Email(message = "{client-email-format}")
    private String email;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
