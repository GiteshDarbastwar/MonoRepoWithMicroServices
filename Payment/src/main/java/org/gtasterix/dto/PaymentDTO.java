package org.gtasterix.dto;

import lombok.Data;

@Data
public class PaymentDTO {
    private Long id;
    private String orderId;
    private double amount;
}
