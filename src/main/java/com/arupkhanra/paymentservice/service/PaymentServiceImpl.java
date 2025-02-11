package com.arupkhanra.paymentservice.service;

import com.arupkhanra.paymentservice.entity.TransactionDetails;
import com.arupkhanra.paymentservice.model.PaymentRequest;
import com.arupkhanra.paymentservice.repository.TransactionDetailsRepository;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

        @Autowired
        private  TransactionDetailsRepository transactionDetailsRepository;

        @Override
        public long doPayment(PaymentRequest paymentRequest) {
            log.info("Processing Payment for Order ID: {}", paymentRequest.getOrderId());

            TransactionDetails transactionDetails = TransactionDetails.builder()
                    .orderId(paymentRequest.getOrderId())
                    .paymentMode(paymentRequest.getPaymentMode().name())
                    .referenceNumber(paymentRequest.getReferenceNumber())
                    .paymentDate(Instant.now())
                    .paymentStatus("SUCCESS")
                    .amount(paymentRequest.getAmount())
                    .build();

            transactionDetailsRepository.save(transactionDetails);
            log.info("Payment Successful for Order ID: {}, Transaction ID: {}", paymentRequest.getOrderId(), transactionDetails.getId());

            return transactionDetails.getId();
        }
}
