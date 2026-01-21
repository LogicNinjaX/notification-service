package com.nitish.notification_service.producer;

import com.nitish.notification_service.entity.OutBoxEvent;
import com.nitish.notification_service.enums.EventStatus;
import com.nitish.notification_service.repository.OutBoxEventRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationMessageProducer {

    private final OutBoxEventRepository eventRepository;
    private final KafkaTemplate<String, OutBoxEvent> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(NotificationMessageProducer.class);

    public NotificationMessageProducer(OutBoxEventRepository eventRepository, KafkaTemplate<String, OutBoxEvent> kafkaTemplate) {
        this.eventRepository = eventRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void produceMessage() {
        List<OutBoxEvent> eventList = eventRepository.getEventsByStatus(EventStatus.NEW);

        for (OutBoxEvent event : eventList) {
            kafkaTemplate.send("notification-message", event);

            event.setStatus(EventStatus.SENT);
            eventRepository.save(event);
            logger.info("event published successfully [event id={}, aggregate id={}, event type={}]", event.getEventId(), event.getAggregateId(), event.getEventType());
        }
    }
}
