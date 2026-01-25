package com.nitish.notification_service.messaging.consumer;

import com.nitish.notification_service.entity.NotificationDelivery;
import com.nitish.notification_service.entity.NotificationMessage;
import com.nitish.notification_service.entity.OutBoxEvent;
import com.nitish.notification_service.enums.MessageStatus;
import com.nitish.notification_service.enums.NotificationChannel;
import com.nitish.notification_service.exception.custom_exception.EntityNotFoundException;
import com.nitish.notification_service.repository.NotificationDeliveryRepository;
import com.nitish.notification_service.repository.NotificationMessageRepository;
import com.nitish.notification_service.repository.OutBoxEventRepository;
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
    private final OutBoxEventRepository eventRepository;

    private final int maxAttempts;

    public NotificationMessageListener(NotificationMessageRepository messageRepository, NotificationDispatcher notificationDispatcher, NotificationDeliveryRepository deliveryRepository, OutBoxEventRepository eventRepository, @Value("${app.notification.max-attempts}") int maxAttempts) {
        this.messageRepository = messageRepository;
        this.notificationDispatcher = notificationDispatcher;
        this.deliveryRepository = deliveryRepository;
        this.eventRepository = eventRepository;
        this.maxAttempts = maxAttempts;
    }

    @KafkaListener(topics = "notification-message", groupId = "notification-group")
    public void consume(OutBoxEvent event) {
        logger.info("received event [event id={}, aggregate id={}, event type={}]", event.getEventId(), event.getAggregateId(), event.getEventType());

        UUID messageId = event.getAggregateId();
        NotificationMessage message = messageRepository.findMessageWithRequestEntity(messageId)
                .orElseThrow(() -> new EntityNotFoundException("notification message", messageId));

        NotificationChannel channel = message.getRequest().getChannel();
        int attempt = message.getRetryCount() + 1;

        try {
            notificationDispatcher.dispatch(message);
            deliveryRepository.save(NotificationDelivery.success(message, channel, attempt));

            message.setStatus(MessageStatus.SENT);
            messageRepository.save(message);
        } catch (Exception e) {
            deliveryRepository.save(NotificationDelivery.failure(message, channel, e.getMessage(), attempt));
            message.setStatus(MessageStatus.FAILED);

            message.setRetryCount(message.getRetryCount() + 1);

            if (message.getRetryCount() >= maxAttempts){
                message.setStatus(MessageStatus.DEAD);
            } else {
                eventRepository.save(OutBoxEvent.retry(messageId));
            }

            messageRepository.save(message);
            logger.error(
                    "notification failed [messageId={}, attempt={}]",
                    messageId,
                    attempt,
                    e
            );
        }
    }


}
