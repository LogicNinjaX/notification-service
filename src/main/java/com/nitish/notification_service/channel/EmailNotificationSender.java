package com.nitish.notification_service.channel;

import com.nitish.notification_service.entity.NotificationMessage;
import com.nitish.notification_service.enums.NotificationChannel;
import com.nitish.notification_service.exception.custom_exception.NotificationException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationSender implements NotificationSender {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationSender.class);
    private final JavaMailSender mailSender;

    public EmailNotificationSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.EMAIL;
    }

    @Override
    public void send(NotificationMessage message) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(message.getRecipient());
            messageHelper.setSubject(message.getRenderedSubject());
            messageHelper.setText(message.getRenderedContent(), true);
            mailSender.send(mimeMessage);
            logger.info("Email notification sent successfully [receivers email={}]", message.getRecipient());
        } catch (MessagingException e) {
            logger.error("Failed to send notification [receivers email={}, message id={}]", message.getRecipient(), message.getMessageId());
            throw new NotificationException("Failed to send notification to:" + message.getRecipient() + " message id:" + message.getMessageId() + " more info:" + e.getMessage());
        }
    }
}
