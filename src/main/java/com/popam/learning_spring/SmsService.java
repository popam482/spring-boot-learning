package com.popam.learning_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Profile("dev")
@ConditionalOnProperty(name = "app.sms.enabled", havingValue = "true")
public class SmsService implements MessageService {

    private final DateTimeFormatter dateTimeFormatter;
    private final String prefix;

    @Autowired
    public SmsService(DateTimeFormatter dateTimeFormatter, @Value("${app.sms.prefix}") String prefix) {
        this.dateTimeFormatter = dateTimeFormatter;
        this.prefix = prefix;
    }

    @Override
    public void sendMessage(String message) {
        String currentDate = LocalDateTime.now().format(dateTimeFormatter);
        System.out.println(prefix + ": " + message + " " + currentDate);
    }
}
