package com.nitish.notification_service.messaging.producer;

import com.nitish.notification_service.entity.OutBoxEvent;
import com.nitish.notification_service.enums.EventStatus;
import com.nitish.notification_service.repository.OutBoxEventRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutBoxEventProducer {

    private static final Logger logger = LoggerFactory.getLogger(OutBoxEventProducer.class);

    private final KafkaTemplate<String, OutBoxEvent> kafkaTemplate;
    private final OutBoxEventRepository eventRepository;

    public OutBoxEventProducer(KafkaTemplate<String, OutBoxEvent> kafkaTemplate, OutBoxEventRepository eventRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.eventRepository = eventRepository;
    }

    @Transactional
    public void produceOutBoxEvent(){
        List<OutBoxEvent> eventList = eventRepository
                .getEventsByStatus(EventStatus.NEW, PageRequest.ofSize(50));

        for (OutBoxEvent event : eventList) {
            kafkaTemplate.send("notification-message", event);

            event.setStatus(EventStatus.SENT);
            eventRepository.save(event);
            logger.info(
                    "outbox event published [eventId={}, aggregateId={}]", event.getEventId(), event.getAggregateId()
            );
        }
    }
}
