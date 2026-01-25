package com.nitish.notification_service.producer;

import com.nitish.notification_service.entity.OutBoxEvent;
import com.nitish.notification_service.enums.EventStatus;
import com.nitish.notification_service.repository.OutBoxEventRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutBoxPublisher {

    private static final Logger logger = LoggerFactory.getLogger(OutBoxPublisher.class);
    private final OutBoxEventRepository eventRepository;
    private final KafkaTemplate<String, OutBoxEvent> kafkaTemplate;

    public OutBoxPublisher(OutBoxEventRepository eventRepository, KafkaTemplate<String, OutBoxEvent> kafkaTemplate) {
        this.eventRepository = eventRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    @Scheduled(fixedDelay = 60000)
    public void publish() {
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
