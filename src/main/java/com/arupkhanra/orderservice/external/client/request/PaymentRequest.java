package com.arupkhanra.orderservice.external.client.request;

import com.arupkhanra.orderservice.model.PaymentMode;
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
