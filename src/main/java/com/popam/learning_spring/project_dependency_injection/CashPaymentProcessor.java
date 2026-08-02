package com.popam.learning_spring.project_dependency_injection;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;


@Component
public class CashPaymentProcessor implements PaymentProcessor {

    Logger logger;
    DecimalFormat df;

    public CashPaymentProcessor(Logger logger, DecimalFormat df) {
        this.logger = logger;
        this.df = df;
    }

    @PostConstruct
    public void init() {
        System.out.println("Cash payment service: postconstruct - constructor has already been called");
    }

    @Override
    public boolean processPayment(double amount) {
        logger.log("Cash payment successful: " + amount);
        return true;
    }
}
