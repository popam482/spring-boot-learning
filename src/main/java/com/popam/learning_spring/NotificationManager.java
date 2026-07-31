package com.popam.learning_spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {

    MessageService messageService;

    @Autowired
    public NotificationManager(@Qualifier("smsService") MessageService messageService) {
        this.messageService = messageService;
    }

    public void notifyUser() {
        messageService.sendMessage("Hello World");
    }
}
