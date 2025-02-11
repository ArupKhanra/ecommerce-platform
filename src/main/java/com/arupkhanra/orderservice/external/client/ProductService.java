package com.arupkhanra.orderservice.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE", url = "http://localhost:8080")
public interface ProductService {
    @PutMapping("/productService/products/reduceQuantity/{id}")
    ResponseEntity<?> reduceQuantity(
            @PathVariable("id") long productId, @RequestParam long quantity
    );
}