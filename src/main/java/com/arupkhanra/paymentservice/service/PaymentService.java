package com.arupkhanra.paymentservice.service;

import com.arupkhanra.paymentservice.model.PaymentRequest;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);
}
