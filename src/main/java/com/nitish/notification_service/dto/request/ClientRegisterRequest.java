package com.nitish.notification_service.dto.request;

public record ClientRegisterRequest(ClientDetails clientDetails, LoginDetails loginDetails) {
}
