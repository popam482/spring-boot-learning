package com.popam.learning_spring.project;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Primary
@Component
public class CardPaymentProcessor implements PaymentProcessor {

    Logger logger;
    DecimalFormat df;

    public CardPaymentProcessor(Logger logger, DecimalFormat df) {
        this.logger = logger;
        this.df = df;
    }

    @Override
    public boolean processPayment(double amount) {
        //System.out.println("Card payment successful: " + amount);
        String amountFormater = df.format(amount);
        logger.log("Card payment successful: " + amountFormater);
        return true;
    }
}
