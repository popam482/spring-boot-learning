package com.popam.learning_spring;

import org.springframework.stereotype.Component;

@Component
public class SmsService implements MessageService{

    @Override
    public void sendMessage(String message) {
        System.out.println("SMS message: "+message);
    }
}
