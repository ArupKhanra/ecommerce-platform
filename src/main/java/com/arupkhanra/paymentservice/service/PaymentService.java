package com.arupkhanra.paymentservice.service;

import com.arupkhanra.paymentservice.model.PaymentRequest;
import com.arupkhanra.paymentservice.model.PaymentResponse;

public interface PaymentService {
    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(String orderId);
}
