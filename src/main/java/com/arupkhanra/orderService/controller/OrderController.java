package com.arupkhanra.orderService.controller;

import com.arupkhanra.orderService.model.OrderRequest;
import com.arupkhanra.orderService.service.OrderService;
import lombok.Data;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/orders")
@Log4j2
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        long orderId = orderService.placeOrder(orderRequest);
       log.info("Order Id : {}",orderId);
        return new ResponseEntity<>(orderId ,HttpStatus.CREATED);
    }
}
