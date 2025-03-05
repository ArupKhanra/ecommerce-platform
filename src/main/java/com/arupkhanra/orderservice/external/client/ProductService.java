package com.arupkhanra.orderservice.external.client;

import com.arupkhanra.orderservice.exception.CustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CircuitBreaker(name = "external", fallbackMethod = "fallback")
@FeignClient(name = "PRODUCT-SERVICE", url = "http://localhost:8080")
public interface ProductService {
    @PutMapping("/products/reduceQuantity/{id}")
    ResponseEntity<?> reduceQuantity(
            @PathVariable("id") long productId, @RequestParam long quantity
    );

    default void fallback(){
        throw new CustomException("product service is not available","UNAVAILABLE",500);
    }
}