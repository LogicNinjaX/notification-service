package com.nitish.notification_service.repository;

import com.nitish.notification_service.entity.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, UUID> {
}
