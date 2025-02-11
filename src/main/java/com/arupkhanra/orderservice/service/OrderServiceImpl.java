package com.arupkhanra.orderservice.service;

import com.arupkhanra.orderservice.entity.Order;
import com.arupkhanra.orderservice.external.client.PaymentService;
import com.arupkhanra.orderservice.external.client.ProductService;
import com.arupkhanra.orderservice.external.client.request.PaymentRequest;
import com.arupkhanra.orderservice.model.OrderRequest;
import com.arupkhanra.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;
    @Override
    public long placeOrder(OrderRequest orderRequest) {

        log.info("placing order request: {}",orderRequest);
        productService.reduceQuantity(orderRequest.getProductId(),orderRequest.getQuantity());
        log.info("Creating Order with Status CREATED");
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())  // ✅ Correct field name
                .quantity(orderRequest.getQuantity())
                .build();
            order = orderRepository.save(order);
            log.info("Calling Payment Service to complete the payment");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .referenceNumber(UUID.randomUUID().toString()) // ✅ Generate unique reference number
                .build();

        String orderStatus = null;
        try{
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully. Changing the Order status to placed");
            orderStatus = "PLACED";
        }catch (Exception e){
            log.info("Error occurred in the payment. changing order status PAYMENT_FAILED ");
            orderStatus = "PAYMENT_FAILED";
        }


        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
            log.info("Oder place successfully with order id : {}",order.getId());
        return order.getId();
    }
}
