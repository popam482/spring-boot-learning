package com.popam.learning_spring.basics_dependency_injection;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestComponent {

    @Autowired
    private MessageService messageService;

    public TestComponent() {
        System.out.println("Constructor runnnig...");
        //messageService.sendMessage("test"); will give an error: messageService is still null
    }

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct running, messageService: " + messageService);
    }
}