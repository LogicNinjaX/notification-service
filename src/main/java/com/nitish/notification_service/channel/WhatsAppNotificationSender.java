package com.nitish.notification_service.channel;

import com.nitish.notification_service.entity.NotificationMessage;
import com.nitish.notification_service.enums.NotificationChannel;
import com.nitish.notification_service.exception.custom_exception.NotificationException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WhatsAppNotificationSender implements NotificationSender {

    private static final Logger logger = LoggerFactory.getLogger(WhatsAppNotificationSender.class);

    @Value("${app.notification.channel-type.whatsapp.from-number}")
    private String fromNumber;

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.WHATSAPP;
    }

    @Override
    public void send(NotificationMessage message) {
        try {
            Message whatsAppMessage = Message.creator(
                    new PhoneNumber("whatsapp:" + message.getRecipient()),
                    new PhoneNumber(fromNumber),
                    message.getRenderedContent()
            ).create();

            logger.info("whatsapp notification sent successfully to: {}, sent at: {}", message.getRecipient(), whatsAppMessage.getDateSent());
        } catch (Exception e) {
            logger.error("failed to send whatsapp notification to: {}", message.getRecipient(), e);
            throw new NotificationException(e.getMessage());
        }
    }
}
