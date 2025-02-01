package com.arupkhanra.orderService.external.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PRODUCT-SERVICE", url = "http://localhost:8080/productService/products")
public interface ProductService {
    @PutMapping("/reduceQuantity/{id}")
    ResponseEntity<?> reduceQuantity(
            @PathVariable("id") long productId, @RequestParam long quantity
    );
}