package com.popam.learning_spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig {

    @Value("${app.notification.date-format}") // @Value("${app.notification.date-format:dd/MM/yyyy}")
    private String dateFormat;

    @Bean
    public DateTimeFormatter dateTimeFormatter() {
        return DateTimeFormatter.ofPattern(dateFormat);
    }
}
