package com.popam.learning_spring;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EmailService implements MessageService {

    @Override
    public void sendMessage(String message) {
        System.out.println("Email message: "+message);
    }
}
