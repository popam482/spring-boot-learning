package com.popam.learning_spring.project;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;


@Component
public class CashPaymentProcessor implements PaymentProcessor {

    Logger logger;

    public CashPaymentProcessor(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean processPayment(double amount) {
        //System.out.println("Cash payment successful: " + amount);
        logger.log("Cash payment successful: " + amount);
        return true;
    }
}
