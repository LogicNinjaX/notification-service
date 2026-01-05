package com.nitish.notification_service.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClientUpdateRequest {

    @NotBlank(message = "{client.name.not-blank}")
    private String fullName;

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
