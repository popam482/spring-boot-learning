package com.popam.learning_spring.basics_dependency_injection;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
//@Primary
@Profile("prod")
public class EmailService implements MessageService {

    private final DateTimeFormatter dateTimeFormatter;
    private final String prefix;


    public EmailService(DateTimeFormatter dateTimeFormatter, @Value("${app.email.prefix:E-mail default}") String prefix) {
        this.dateTimeFormatter = dateTimeFormatter;
        this.prefix = prefix;
    }

    @PostConstruct
    public void init() {
        System.out.println("Email service: postconstruct - constructor has already been called");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Email service: preDestroy - has been called");
    }

    @Override
    public void sendMessage(String message) {
        String currentDate = LocalDateTime.now().format(dateTimeFormatter);
        System.out.println(prefix + ": " + message + " " + currentDate);
    }
}
