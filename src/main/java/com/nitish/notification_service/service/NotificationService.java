package com.nitish.notification_service.service;

import com.nitish.notification_service.dto.request.SendNotificationRequest;

import java.util.UUID;

public interface NotificationService {

    void sendNotification(UUID userId, SendNotificationRequest request);
}
