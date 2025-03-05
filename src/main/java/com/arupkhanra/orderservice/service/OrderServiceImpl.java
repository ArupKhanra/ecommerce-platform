package com.arupkhanra.orderservice.service;

import com.arupkhanra.orderservice.entity.Order;
import com.arupkhanra.orderservice.exception.CustomException;
import com.arupkhanra.orderservice.external.client.PaymentService;
import com.arupkhanra.orderservice.external.client.ProductService;
import com.arupkhanra.orderservice.external.client.request.PaymentRequest;
import com.arupkhanra.orderservice.external.client.response.PaymentResponse;
import com.arupkhanra.orderservice.external.client.response.ProductResponse;
import com.arupkhanra.orderservice.model.OrderRequest;
import com.arupkhanra.orderservice.model.OrderResponse;
import com.arupkhanra.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Transactional
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing order request: {}", orderRequest);

        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
        log.info("Creating Order with Status CREATED");

        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        order = orderRepository.save(order);
        log.info("Calling Payment Service to complete the payment");

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getId())
                .paymentMode(orderRequest.getPaymentMode())
                .amount(orderRequest.getTotalAmount())
                .referenceNumber(UUID.randomUUID().toString())
                .build();

        String orderStatus;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done successfully. Changing Order status to PLACED");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Error in payment. Changing order status to PAYMENT_FAILED", e);
            orderStatus = "PAYMENT_FAILED";
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Order placed successfully with ID: {}", order.getId());

        return order.getId();
    }

    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Fetching order details for order ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("Order ID not found: " + orderId, "NOT_FOUND", 404));

        log.info("Calling Product Service for Product ID: {}", order.getProductId());
        ProductResponse productDetails = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/products/" + order.getProductId(), ProductResponse.class);

        log.info("getting payment details from the payment service");
        PaymentResponse paymentResponse
                = restTemplate.getForObject("http://PAYMENT-SERVICE/payment/order/"+order.getId(), PaymentResponse.class);

        OrderResponse.ProductDetails productDetailsResponse = OrderResponse.ProductDetails.builder()
                .productName(productDetails.getProductName())
                .productId(productDetails.getProductId())
                .quantity(productDetails.getQuantity())
                .price(productDetails.getPrice())
                .build();

        OrderResponse.PaymentDetails paymentDetails
                = OrderResponse.PaymentDetails.builder()
                .productId(paymentResponse.getPaymentId())
                .paymentStatus(paymentResponse.getStatus())
                .paymentDate(paymentResponse.getPaymentDate())
                .paymentMode(paymentResponse.getPaymentMode())
                .build();

        return OrderResponse.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .orderDate(order.getOrderDate())
                .productDetails(productDetailsResponse)
                .paymentDetails(paymentDetails)
                .build();
    }
}
