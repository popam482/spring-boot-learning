package com.popam.learning_spring.project;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class PaymentService {

    PaymentProcessor paymentProcessor;
    Logger logger;
    DecimalFormat decimalFormat;

    public PaymentService(PaymentProcessor paymentProcessor, Logger logger, DecimalFormat decimalFormat) {
        this.paymentProcessor = paymentProcessor;
        this.logger = logger;
        this.decimalFormat = decimalFormat;
    }

    public boolean processPayment(double amount) {
        paymentProcessor.processPayment(amount);
        return true;
    }
}