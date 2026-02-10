package com.nitish.notification_service.service.impl;

import com.nitish.notification_service.dto.response.NotificationRequestResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import com.nitish.notification_service.enums.RequestStatus;
import com.nitish.notification_service.repository.NotificationRequestRepository;
import com.nitish.notification_service.service.NotificationRequestService;
import com.nitish.notification_service.util.mapper.NotificationRequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationRequestServiceImpl implements NotificationRequestService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationRequestServiceImpl.class);
    private final NotificationRequestRepository requestRepository;
    private final NotificationRequestMapper requestMapper;

    public NotificationRequestServiceImpl(NotificationRequestRepository requestRepository, NotificationRequestMapper requestMapper) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
    }

    @Override
    public PageResponse<NotificationRequestResponse> getAllRequestsByUser(UUID userId, Pageable pageable){
        var pageResponse = requestRepository.findAllRequestsByRequester(userId, pageable)
                .map(requestMapper::toResponse);
        return PageResponse.from(pageResponse);
    }

    @Override
    public void updateRequestStatus(UUID requestId, RequestStatus status){
        int updatedRows = requestRepository.updateRequestStatus(requestId, status);
        if (updatedRows > 0){
            logger.info("Notofication request status updated successfully [request id={}, updated status={}]", requestId, status);
        }
    }
}
