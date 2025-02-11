package com.arupkhanra.paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transaction_details")
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "mode")
    private String paymentMode;
    @Column(name = "reference_number",nullable = false)
    private String referenceNumber;
    @Column(name = "payment_date")
    private Instant paymentDate;
    @Column(name = "payment_status")
    private String paymentStatus;
    @Column(name = "amount")
    private Long amount;
}
