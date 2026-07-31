package com.popam.learning_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SmsService implements MessageService {

    private final DateTimeFormatter dateTimeFormatter;

    @Autowired
    public SmsService(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public void sendMessage(String message) {
        String currentDate = LocalDateTime.now().format(dateTimeFormatter);
        System.out.println("SMS message: " + message + " " + currentDate);
    }
}
