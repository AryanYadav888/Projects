package com.ecommerce.payment;

public interface PaymentMethod {
    boolean processPayment(Double amount,String paymentDetails);
    String getPaymentMethodName();
}
