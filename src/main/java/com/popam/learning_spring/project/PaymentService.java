package com.popam.learning_spring.project;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class PaymentService {

    private PaymentProcessor paymentProcessor;
    private Logger logger;
    private DecimalFormat decimalFormat;
    private double feePercent;

    public PaymentService(@Qualifier("cashPaymentProcessor") PaymentProcessor paymentProcessor, Logger logger, DecimalFormat decimalFormat, @Value("${app.payment.fee-percent:2.5}") double feePercent) {
        this.paymentProcessor = paymentProcessor;
        this.logger = logger;
        this.decimalFormat = decimalFormat;
        this.feePercent = feePercent;
    }

    private double calculateFinalAmount(double initialAmount) {
        return (initialAmount + initialAmount * feePercent / 100);
    }

    public void processPayment(double amount) {
        double finalAmount = calculateFinalAmount(amount);
        boolean success = paymentProcessor.processPayment(finalAmount);
        if (success) {
            String formattedAmount = decimalFormat.format(finalAmount);
            logger.log("Payment processing ended" + formattedAmount);
        }
    }
}