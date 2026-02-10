package com.nitish.notification_service.repository;

import com.nitish.notification_service.entity.NotificationDelivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface NotificationDeliveryRepository extends JpaRepository<NotificationDelivery, UUID> {

    @Query("""
            SELECT nd FROM NotificationDelivery nd
            WHERE nd.message.messageId = :messageId
            """)
    Optional<NotificationDelivery> findByMessageId(UUID messageId);

    @Query("""
            SELECT nd FROM NotificationDelivery nd
            WHERE nr.message.request.requestedBy.userId = :userId
            AND nr.message.request.requestId = :requestId
            """)
    Page<NotificationDelivery> findDeliveriesByUserIdAndRequestId(UUID userId, UUID requestId, Pageable pageable);

    @Query("""
            SELECT nd FROM NotificationDelivery nd
            AND nr.message.request.requestId = :requestId
            """)
    Page<NotificationDelivery> findDeliveriesByRequestId(UUID requestId, Pageable pageable);
}
