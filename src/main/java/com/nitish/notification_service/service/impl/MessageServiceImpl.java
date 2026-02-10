package com.nitish.notification_service.service.impl;

import com.nitish.notification_service.dto.response.MessageResponse;
import com.nitish.notification_service.dto.response.PageResponse;
import com.nitish.notification_service.repository.NotificationMessageRepository;
import com.nitish.notification_service.service.MessageService;
import com.nitish.notification_service.util.mapper.MessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    private final NotificationMessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageServiceImpl(NotificationMessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public PageResponse<MessageResponse> getMessagesByRequestId(UUID requestId, Pageable pageable){
        var response = messageRepository.findAllMessagesByRequestId(requestId, pageable)
                .map(messageMapper::toResponse);
        return PageResponse.from(response);
    }

    @Override
    public PageResponse<MessageResponse> getMessagesByRequestIdAndUserId(UUID requestId, UUID userId, Pageable pageable){
        var response = messageRepository.findAllMessagesByRequestIdAndUserId(requestId, userId, pageable)
                .map(messageMapper::toResponse);
        return PageResponse.from(response);
    }
}
