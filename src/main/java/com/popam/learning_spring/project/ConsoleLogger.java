package com.popam.learning_spring.project;

import org.springframework.context.annotation.Profile;

@Profile("dev")
public class ConsoleLogger implements Logger{
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
