package com.nitish.notification_service.scheduler;

import com.nitish.notification_service.messaging.producer.OutBoxEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OutBoxEventScheduler {

    private static final Logger logger = LoggerFactory.getLogger(OutBoxEventScheduler.class);
    private final OutBoxEventProducer outBoxEventProducer;

    public OutBoxEventScheduler(OutBoxEventProducer outBoxEventProducer) {
        this.outBoxEventProducer = outBoxEventProducer;
    }


    @Scheduled(fixedDelay = 60000)
    public void run(){
        outBoxEventProducer.produceOutBoxEvent();
    }
}
