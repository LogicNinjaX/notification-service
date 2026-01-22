package com.nitish.notification_service.repository;

import com.nitish.notification_service.entity.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, UUID> {

    @Query("""
            SELECT nm FROM NotificationMessage nm
            JOIN FETCH nm.request
            LEFT JOIN FETCH nm.notificationDeliveries
            WHERE nm.messageId = :messageId
            """)
    Optional<NotificationMessage> findMessageWithRequestEntity(UUID messageId);
}
