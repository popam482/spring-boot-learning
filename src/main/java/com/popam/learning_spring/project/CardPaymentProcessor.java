package com.popam.learning_spring.project;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class CardPaymentProcessor implements PaymentProcessor {

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Card payment successful: " + amount);
        return true;
    }
}
