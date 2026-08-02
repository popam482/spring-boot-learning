package com.popam.learning_spring.project;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class CardPaymentProcessor implements PaymentProcessor {

    Logger logger;

    public CardPaymentProcessor(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean processPayment(double amount) {
        //System.out.println("Card payment successful: " + amount);
        logger.log("Card payment successful: " + amount);
        return true;
    }
}
