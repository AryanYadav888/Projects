package com.ecommerce.payment;

import org.springframework.stereotype.Component;

@Component("paypalPayment")
public class PayPalPayment implements PaymentMethod{
    @Override
    public boolean processPayment(Double amount, String paymentDetails) {
        System.out.println("Processing PayPal Payment....");
        System.out.println("Amount: $"+amount);
        System.out.println("PayPal Email: "+paymentDetails);

        if(validatePayPalEmail(paymentDetails)){
            System.out.println("PayPal payment successfull!");
            return true;
        }
        System.out.println("PayPal payment failed!");
        return false;
    }

    @Override
    public String getPaymentMethodName() {
        return "PayPal";
    }

    private boolean validatePayPalEmail(String email){
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
