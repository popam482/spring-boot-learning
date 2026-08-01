package com.popam.learning_spring;

import org.springframework.stereotype.Component;

@Component
public class ServiceB {
    public ServiceB(ServiceA serviceA) {
        System.out.println("ServiceB created");
    }
}