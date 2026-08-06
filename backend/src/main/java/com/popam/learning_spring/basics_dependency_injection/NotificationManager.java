package com.popam.learning_spring.basics_dependency_injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {

    MessageService messageService;

    @Autowired
    Counter counter;

    @Autowired
    //public NotificationManager(@Qualifier("emailService")MessageService messageService, Counter counter)
    public NotificationManager(MessageService messageService, Counter counter) {
        this.messageService = messageService;
        this.counter = counter;

    }

    public void showIdentity() {
        System.out.println("MessageService instance: " + messageService.hashCode());
    }

    public void notifyUser() {
        messageService.sendMessage("Hello World");
        counter.increment();
        counter.increment();
    }
}
