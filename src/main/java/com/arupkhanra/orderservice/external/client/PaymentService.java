package com.arupkhanra.orderservice.external.client;

import com.arupkhanra.orderservice.exception.CustomException;
import com.arupkhanra.orderservice.external.client.request.PaymentRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PAYMENT-SERVICE", url = "http://localhost:8081/payment")
public interface PaymentService {
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest);

    default void fallback(){
        throw new CustomException("payment service is not available","UNAVAILABLE",500);
    }
}

