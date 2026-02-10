package com.nitish.notification_service.service;

import com.nitish.notification_service.dto.response.NotificationRequestResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import com.nitish.notification_service.enums.RequestStatus;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationRequestService {

    PageResponse<NotificationRequestResponse> getAllRequestsByUser(UUID userId, Pageable pageable);

    void updateRequestStatus(UUID requestId, RequestStatus status);
}
