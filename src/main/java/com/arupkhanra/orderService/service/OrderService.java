package com.arupkhanra.orderService.service;

import com.arupkhanra.orderService.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
