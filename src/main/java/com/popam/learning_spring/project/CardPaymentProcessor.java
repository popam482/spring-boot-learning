package com.popam.learning_spring.project;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Primary
@Component
public class CardPaymentProcessor implements PaymentProcessor {

    Logger logger;

    public CardPaymentProcessor(Logger logger, DecimalFormat df) {
        this.logger = logger;
    }

    @PostConstruct
    public void init() {
        System.out.println("Card payment service: postconstruct - constructor has already been called");
    }

    @Override
    public boolean processPayment(double amount) {
        logger.log("Card payment successful: " + amount);
        return true;
    }
}
