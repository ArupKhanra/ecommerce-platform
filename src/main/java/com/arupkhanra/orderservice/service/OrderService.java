package com.arupkhanra.orderservice.service;

import com.arupkhanra.orderservice.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
