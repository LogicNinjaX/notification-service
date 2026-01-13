package com.nitish.notification_service.repository;

import com.nitish.notification_service.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TemplateRepository extends JpaRepository<NotificationTemplate, UUID> {
}
