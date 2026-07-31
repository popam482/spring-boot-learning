package com.popam.learning_spring;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Primary
public class EmailService implements MessageService {

    private final DateTimeFormatter dateTimeFormatter;


    public EmailService(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

    @Override
    public void sendMessage(String message) {
        String currentDate = LocalDateTime.now().format(dateTimeFormatter);
        System.out.println("Email message: " + message + " " + currentDate);
    }
}
