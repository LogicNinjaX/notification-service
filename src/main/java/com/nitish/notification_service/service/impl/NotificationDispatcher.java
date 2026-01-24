package com.nitish.notification_service.service.impl;

import com.nitish.notification_service.channel.NotificationSender;
import com.nitish.notification_service.channel.NotificationSenderFactory;
import com.nitish.notification_service.entity.NotificationMessage;
import org.springframework.stereotype.Service;

@Service
public class NotificationDispatcher {

    private final NotificationSenderFactory senderFactory;

    public NotificationDispatcher(NotificationSenderFactory senderFactory) {
        this.senderFactory = senderFactory;
    }

    public void dispatch(NotificationMessage message){
        NotificationSender sender = senderFactory.getSender(message.getRequest().getChannel());
        sender.send(message);
    }
}
