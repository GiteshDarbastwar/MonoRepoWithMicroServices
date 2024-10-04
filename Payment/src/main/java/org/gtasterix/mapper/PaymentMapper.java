package org.gtasterix.mapper;

import org.gtasterix.dto.PaymentDTO;
import org.gtasterix.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    // Convert Payment to PaymentDTO
    public PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setOrderId(payment.getOrderId());
        dto.setAmount(payment.getAmount());
        return dto;
    }

    // Convert PaymentDTO to Payment
    public Payment convertToEntity(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setId(paymentDTO.getId()); // If you want to set the ID for updates
        payment.setOrderId(paymentDTO.getOrderId());
        payment.setAmount(paymentDTO.getAmount());
        return payment;
    }
}
