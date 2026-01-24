package com.nitish.notification_service.channel;

import com.nitish.notification_service.enums.NotificationChannel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class NotificationSenderFactory {

    private final Map<NotificationChannel, NotificationSender> senders;

    public NotificationSenderFactory(List<NotificationSender> senderList) {
        this.senders = senderList.stream()
                .collect(Collectors.toMap(NotificationSender::channel, Function.identity()));
    }

    public NotificationSender getSender(NotificationChannel channel){
        return Optional.ofNullable(senders.get(channel))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported channel: " + channel));
    }


}
