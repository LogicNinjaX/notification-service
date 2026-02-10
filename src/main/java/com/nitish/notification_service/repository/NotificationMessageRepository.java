package com.nitish.notification_service.repository;

import com.nitish.notification_service.entity.NotificationMessage;
import com.nitish.notification_service.entity.NotificationRequest;
import com.nitish.notification_service.enums.MessageStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
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

    @Query("""
            SELECT nm FROM NotificationMessage nm
            WHERE nm.retryCount < :maxRetries
            AND nm.status = :status
            """)
    List<NotificationMessage> findAllRetryableMessages(int maxRetries, MessageStatus status, Pageable pageable);

    boolean existsByRequestAndStatusIn(NotificationRequest request, Collection<MessageStatus> statuses);

    @Query("""
            SELECT nm FROM NotificationMessage nm
            WHERE nm.request.requestId = :requestId
            """)
    Page<NotificationMessage> findAllMessagesByRequestId(UUID requestId, Pageable pageable);

    @Query("""
            SELECT nm FROM NotificationMessage nm
            WHERE nm.request.requestId = :requestId
            AND nm.request.requestedBy.userId = :userId
            """)
    Page<NotificationMessage> findAllMessagesByRequestIdAndUserId(UUID requestId, UUID userId, Pageable pageable);
}
