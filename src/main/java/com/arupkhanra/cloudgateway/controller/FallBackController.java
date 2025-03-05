package com.arupkhanra.cloudgateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @GetMapping("/orderServiceFallBack")
    public String orderServiceFallback(){
        return "Order service is Down!";
    }

    @GetMapping("/paymentServiceFallBack")
    public String paymentServiceFallback(){
        return "Payment service is Down!";
    }

    @GetMapping("/productServiceFallBack")
    public String productServiceFallback(){
        return "Product service is Down!";
    }
}
