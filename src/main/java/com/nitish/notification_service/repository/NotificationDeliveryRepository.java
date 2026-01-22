package com.nitish.notification_service.repository;

import com.nitish.notification_service.entity.NotificationDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface NotificationDeliveryRepository extends JpaRepository<NotificationDelivery, UUID> {

    @Query("""
            select nd from NotificationDelivery nd
            where nd.message.messageId = :messageId
            """)
    Optional<NotificationDelivery> findByMessageId(UUID messageId);
}
