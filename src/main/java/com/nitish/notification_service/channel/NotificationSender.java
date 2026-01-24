package com.nitish.notification_service.channel;

import com.nitish.notification_service.entity.NotificationMessage;
import com.nitish.notification_service.entity.OutBoxEvent;
import com.nitish.notification_service.enums.NotificationChannel;

public interface NotificationSender {

    NotificationChannel channel();

    void send(NotificationMessage message);
}
