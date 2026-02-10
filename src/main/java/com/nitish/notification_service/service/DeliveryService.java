package com.nitish.notification_service.service;

import com.nitish.notification_service.dto.response.DeliveryResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DeliveryService {
    DeliveryResponse getDeliveryByMessageId(UUID messageId);

    PageResponse<DeliveryResponse> findDeliveriesByUserIdAndRequestId(UUID userId, UUID requestId, Pageable pageable);

    PageResponse<DeliveryResponse> findDeliveriesByRequestId(UUID requestId, Pageable pageable);
}
