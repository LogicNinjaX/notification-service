package com.nitish.notification_service.consumer;

import com.nitish.notification_service.entity.NotificationDelivery;
import com.nitish.notification_service.entity.NotificationMessage;
import com.nitish.notification_service.entity.OutBoxEvent;
import com.nitish.notification_service.enums.MessageStatus;
import com.nitish.notification_service.enums.NotificationChannel;
import com.nitish.notification_service.exception.custom_exception.EntityNotFoundException;
import com.nitish.notification_service.repository.NotificationDeliveryRepository;
import com.nitish.notification_service.repository.NotificationMessageRepository;
import com.nitish.notification_service.service.impl.NotificationDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationMessageListener.class);
    private final NotificationMessageRepository messageRepository;
    private final NotificationDispatcher notificationDispatcher;
    private final NotificationDeliveryRepository deliveryRepository;

    private final int maxAttempts;

    public NotificationMessageListener(NotificationMessageRepository messageRepository, NotificationDispatcher notificationDispatcher, NotificationDeliveryRepository deliveryRepository, @Value("${app.notification.max-attempts}") int maxAttempts) {
        this.messageRepository = messageRepository;
        this.notificationDispatcher = notificationDispatcher;
        this.deliveryRepository = deliveryRepository;
        this.maxAttempts = maxAttempts;
    }

    @KafkaListener(topics = "notification-message", groupId = "notification-group")
    public void consume(OutBoxEvent event) {
        logger.info("received event [event id={}, aggregate id={}, event type={}]", event.getEventId(), event.getAggregateId(), event.getEventType());

        UUID messageId = event.getAggregateId();
        NotificationMessage notificationMessage = messageRepository.findMessageWithRequestEntity(messageId, maxAttempts)
                .orElseThrow(() -> new EntityNotFoundException("notification message", messageId));

        int retryCount = notificationMessage.getRetryCount();
        notificationMessage.setRetryCount(notificationMessage.getRetryCount() + 1); // updating retry count

        NotificationChannel channel = notificationMessage.getRequest().getChannel();

        try {
            notificationDispatcher.dispatch(notificationMessage);
            NotificationDelivery delivery = NotificationDelivery.success(notificationMessage, channel, retryCount);
            deliveryRepository.save(delivery);

            notificationMessage.setStatus(MessageStatus.SENT);
            messageRepository.save(notificationMessage);
        } catch (Exception e) {
            NotificationDelivery delivery = NotificationDelivery.failure(notificationMessage, channel, e.getMessage(), retryCount);
            deliveryRepository.save(delivery);
            messageRepository.save(notificationMessage);
            throw e;
        }
    }


}
