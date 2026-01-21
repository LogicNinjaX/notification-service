package com.nitish.notification_service.consumer;

import com.nitish.notification_service.entity.OutBoxEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationMessageListener.class);

    @KafkaListener(topics = "notification-message", groupId = "notification-group")
    public void consume(OutBoxEvent event){
        logger.info("received event [event id={}, aggregate id={}, event type={}]", event.getEventId(), event.getAggregateId(), event.getEventType());
    }
}
