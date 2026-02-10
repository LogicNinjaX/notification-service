package com.nitish.notification_service.service.impl;

import com.nitish.notification_service.dto.response.DeliveryResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import com.nitish.notification_service.exception.custom_exception.EntityNotFoundException;
import com.nitish.notification_service.repository.NotificationDeliveryRepository;
import com.nitish.notification_service.service.DeliveryService;
import com.nitish.notification_service.util.mapper.DeliveryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryServiceImpl.class);
    private final NotificationDeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;

    public DeliveryServiceImpl(NotificationDeliveryRepository deliveryRepository, DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
    }

    @Override
    public DeliveryResponse getDeliveryByMessageId(UUID messageId){
        var delivery = deliveryRepository.findByMessageId(messageId)
                .orElseThrow(() -> new EntityNotFoundException("delivery", messageId));
        return deliveryMapper.toResponse(delivery);
    }

    @Override
    public PageResponse<DeliveryResponse> findDeliveriesByUserIdAndRequestId(UUID userId, UUID requestId, Pageable pageable){
        var deliveries = deliveryRepository.findDeliveriesByUserIdAndRequestId(userId, requestId, pageable)
                .map(deliveryMapper::toResponse);
        return PageResponse.from(deliveries);
    }

    @Override
    public PageResponse<DeliveryResponse> findDeliveriesByRequestId(UUID requestId, Pageable pageable){
        var deliveries = deliveryRepository.findDeliveriesByRequestId(requestId, pageable)
                .map(deliveryMapper::toResponse);
        return PageResponse.from(deliveries);
    }
}
