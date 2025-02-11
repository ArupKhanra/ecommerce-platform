package com.arupkhanra.paymentservice.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private long orderId;
    private long amount;
    private String  referenceNumber;
    private PaymentMode paymentMode;

}
