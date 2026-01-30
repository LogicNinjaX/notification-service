package com.nitish.notification_service.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${app.notification.channel-type.sms.twilio.sid}")
    private String accountSid;

    @Value("${app.notification.channel-type.sms.twilio.token}")
    private String authToken;

    @PostConstruct
    public void init(){
        Twilio.init(accountSid, authToken);
    }
}
