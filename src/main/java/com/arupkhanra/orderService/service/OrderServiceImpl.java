package com.arupkhanra.orderService.service;

import com.arupkhanra.orderService.entity.Order;
import com.arupkhanra.orderService.model.OrderRequest;
import com.arupkhanra.orderService.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public long placeOrder(OrderRequest orderRequest) {

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .oderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
            order = orderRepository.save(order);
            log.info("Oder place successfully with order id : {}",order.getId());
        return order.getId();
    }
}
