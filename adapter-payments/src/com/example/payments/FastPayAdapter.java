package com.example.payments;

public class FastPayAdapter implements PaymentGateway {
    
    private final FastPayClient client;

    public FastPayAdapter(FastPayClient client) {
        this.client = client;
    }

    @Override
    public String charge(String customerId, int amountCents) {
        // FastPay uses 'payNow' and returns a String
        return client.payNow(customerId, amountCents);
    }
}