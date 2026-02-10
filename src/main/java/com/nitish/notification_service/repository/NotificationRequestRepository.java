package com.nitish.notification_service.repository;

import com.nitish.notification_service.entity.NotificationRequest;
import com.nitish.notification_service.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface NotificationRequestRepository extends JpaRepository<NotificationRequest, UUID> {

    @Query("""
            SELECT nr FROM NotificationRequest nr
            WHERE nr.requestedBy.userId = :userId
            """)
    Page<NotificationRequest> findAllRequestsByRequester(UUID userId, Pageable pageable);

    @Modifying
    @Query("""
            UPDATE NotificationRequest nr
            SET nr.status = :status
            WHERE nr.requestId = :requestId
            """)
    int updateRequestStatus(UUID requestId, RequestStatus status);
}
