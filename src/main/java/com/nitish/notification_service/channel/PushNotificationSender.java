package com.nitish.notification_service.channel;

import com.nitish.notification_service.entity.NotificationMessage;
import com.nitish.notification_service.entity.OutBoxEvent;
import com.nitish.notification_service.enums.NotificationChannel;
import org.springframework.stereotype.Component;

@Component
public class PushNotificationSender implements NotificationSender{
    @Override
    public NotificationChannel channel() {
        return NotificationChannel.PUSH;
    }

    @Override
    public void send(NotificationMessage message) {

    }
}
