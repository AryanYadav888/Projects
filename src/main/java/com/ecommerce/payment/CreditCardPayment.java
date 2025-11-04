package com.ecommerce.payment;

import org.springframework.stereotype.Component;

@Component("creditCardPayment")
public class CreditCardPayment implements PaymentMethod{
    @Override
    public boolean processPayment(Double amount, String paymentDetails) {
        System.out.println("Processing Credit Card Payment....");
        System.out.println("Amount: $"+amount);
        System.out.println("Card Details: "+maskCardnumber(paymentDetails));

        if(validateCreditCard(paymentDetails)){
            System.out.println("Credit card payment Successful!");
            return true;
        }
        System.out.println("Credit card payment failed!");
        return false;
    }

    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }

    private boolean validateCreditCard(String cardNumber){
        return cardNumber != null && cardNumber.replaceAll("\\s","").matches("\\d{16}");
    }

    private String maskCardnumber(String cardNumber){
        if(cardNumber == null || cardNumber.length()< 4){
            return "*****";
        }
        return "**** **** **** "+cardNumber.substring(cardNumber.length()-4);
    }
}
