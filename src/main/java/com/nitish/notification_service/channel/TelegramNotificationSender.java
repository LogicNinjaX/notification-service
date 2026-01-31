package com.nitish.notification_service.channel;

import com.nitish.notification_service.entity.NotificationMessage;
import com.nitish.notification_service.enums.NotificationChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class TelegramNotificationSender implements NotificationSender {

    private static final Logger logger = LoggerFactory.getLogger(TelegramNotificationSender.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final String botToken;

    public TelegramNotificationSender(
            @Value("${app.notification.channel-type.telegram.bot-token}")
            String botToken
    ) {
        this.botToken = botToken;
    }

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.TELEGRAM;
    }

    @Override
    public void send(NotificationMessage message) {
        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";

        Map<String, Object> payload = new HashMap<>();
        payload.put("chat_id", message.getRecipient());
        payload.put("text", message.getRenderedContent());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(payload, headers);

        try {
            restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            logger.info("telegram notification sent successfully [chat id={}]", message.getRecipient());
        } catch (RestClientException e) {
            logger.error("failed to send telegram notification chat id:{}", message.getRecipient(), e);
            throw new RuntimeException(e);
        }
    }
}
