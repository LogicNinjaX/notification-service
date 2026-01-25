package com.nitish.notification_service.service;

import com.nitish.notification_service.entity.NotificationMessage;
import com.nitish.notification_service.entity.OutBoxEvent;
import com.nitish.notification_service.enums.MessageStatus;
import com.nitish.notification_service.repository.NotificationMessageRepository;
import com.nitish.notification_service.repository.OutBoxEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FailedNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(FailedNotificationService.class);
    private final NotificationMessageRepository messageRepository;
    private final OutBoxEventRepository eventRepository;

    private final int maxAttempts;

    public FailedNotificationService(NotificationMessageRepository messageRepository, OutBoxEventRepository eventRepository, @Value("${app.notification.max-attempts}")int maxAttempts) {
        this.messageRepository = messageRepository;
        this.eventRepository = eventRepository;
        this.maxAttempts = maxAttempts;
    }


    @Transactional
    public void createRetryOutboxEvents(){
        List<NotificationMessage> retryableMessages = messageRepository
                .findAllRetryableMessages(maxAttempts, MessageStatus.FAILED, PageRequest.ofSize(50));

        for (NotificationMessage retryableMessage : retryableMessages) {
            OutBoxEvent event = OutBoxEvent.retry(retryableMessage.getMessageId());
            eventRepository.save(event);

            logger.info(
                    "retry outbox event created [messageId={}, retryCount={}]", retryableMessage.getMessageId(), retryableMessage.getRetryCount()
            );
        }
    }
}
