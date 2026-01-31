package com.nitish.notification_service.util;

import com.nitish.notification_service.config.EmailNotificationProperties;
import com.nitish.notification_service.dto.request.SendNotificationRequest;
import com.nitish.notification_service.entity.NotificationTemplate;
import com.nitish.notification_service.exception.custom_exception.InvalidNotificationRequestException;
import com.nitish.notification_service.exception.custom_exception.UnsupportedEmailDomainException;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class EmailValidator {

    private final EmailNotificationProperties properties;

    public EmailValidator(EmailNotificationProperties properties) {
        this.properties = properties;
    }

    public void validate(NotificationTemplate template, SendNotificationRequest request) {
        Set<String> allowedDomains = properties.getAllowedDomains();
        for (String recipient : request.recipients()) {
            if (recipient == null || !recipient.contains("@")) {
                throw new InvalidNotificationRequestException("Invalid email address: " + recipient);
            }

            String domain = extractDomain(recipient);

            if (!allowedDomains.contains(domain.toLowerCase()))
                throw new UnsupportedEmailDomainException("this domain is not currently supported [domain=%s]".formatted(domain));
        }
    }

    private String extractDomain(String email) {
        return email.substring(email.lastIndexOf('@') + 1);
    }
}
