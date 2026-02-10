package com.nitish.notification_service.controller;

import com.nitish.notification_service.dto.request.SendNotificationRequest;
import com.nitish.notification_service.security.CustomUserDetails;
import com.nitish.notification_service.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','CLIENT')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> sendNotification
    (
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody SendNotificationRequest request
    )
    {
        notificationService.sendNotification(user.getUserId(), request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
