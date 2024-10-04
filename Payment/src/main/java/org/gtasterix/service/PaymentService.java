package org.gtasterix.service;

import org.gtasterix.exception.PaymentNotFoundException;
import org.gtasterix.model.Payment;
import org.gtasterix.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Fetch all payments
    public List<Payment> getAllPayments() {
        try {
            return paymentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch payments: " + e.getMessage());
        }
    }

    // Fetch payment by ID
    public Payment getPaymentById(Long id) {
        try {
            return paymentRepository.findById(id)
                    .orElseThrow(() -> new PaymentNotFoundException("Payment not found for ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Error fetching payment by ID: " + e.getMessage());
        }
    }

    // Create a new payment
    public Payment createPayment(Payment payment) {
        try {
            return paymentRepository.save(payment);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create payment: " + e.getMessage());
        }
    }

    // Update a payment
    public Payment updatePayment(Long id, Payment payment) {
        try {
            getPaymentById(id);  // Ensure the payment exists
            payment.setId(id);
            return paymentRepository.save(payment);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update payment: " + e.getMessage());
        }
    }

    // Delete a payment
    public void deletePayment(Long id) {
        try {
            getPaymentById(id);  // Ensure the payment exists
            paymentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete payment: " + e.getMessage());
        }
    }
}
