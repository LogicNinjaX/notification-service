package com.nitish.notification_service.scheduler;

import com.nitish.notification_service.service.FailedNotificationService;
import org.springframework.stereotype.Component;

@Component
public class FailedNotificationScheduler {

    private final FailedNotificationService failedNotificationService;

    public FailedNotificationScheduler(FailedNotificationService failedNotificationService) {
        this.failedNotificationService = failedNotificationService;
    }


    //@Scheduled(fixedDelayString = "${app.scheduler.failed-notification-delay}")
    public void run(){
        failedNotificationService.createRetryOutboxEvents();
    }
}
