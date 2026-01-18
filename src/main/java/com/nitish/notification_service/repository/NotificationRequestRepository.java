package com.nitish.notification_service.repository;

import com.nitish.notification_service.entity.NotificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRequestRepository extends JpaRepository<NotificationRequest, UUID> {
}
