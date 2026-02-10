package com.nitish.notification_service.service;

import com.nitish.notification_service.dto.response.MessageResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MessageService {

    PageResponse<MessageResponse> getMessagesByRequestId(UUID requestId, Pageable pageable);
    PageResponse<MessageResponse> getMessagesByRequestIdAndUserId(UUID requestId, UUID userId, Pageable pageable);
}
