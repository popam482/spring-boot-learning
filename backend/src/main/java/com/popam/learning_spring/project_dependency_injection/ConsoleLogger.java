package com.popam.learning_spring.project_dependency_injection;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class ConsoleLogger implements Logger{
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
