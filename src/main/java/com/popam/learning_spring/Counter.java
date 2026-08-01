package com.popam.learning_spring;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class Counter {
    private int count = 0;

    public void increment() {
        count++;
        System.out.println("Count: " + count + " | instance: " + this);
    }
}
