package org.gtasterix.controller;

import org.gtasterix.dto.PaymentDTO;
import org.gtasterix.mapper.PaymentMapper;
import org.gtasterix.service.PaymentService;
import org.gtasterix.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentMapper paymentMapper;

    // Get all payments
    @GetMapping
    public ResponseEntity<Response> getAllPayments() {
        try {
            List<PaymentDTO> payments = paymentService.getAllPayments()
                    .stream()
                    .map(paymentMapper::convertToDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new Response("Success", payments, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to fetch payments", e.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getPaymentById(@PathVariable Long id) {
        try {
            PaymentDTO payment = paymentMapper.convertToDTO(paymentService.getPaymentById(id));
            return new ResponseEntity<>(new Response("Success", payment, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to fetch payment", e.getMessage(), true), HttpStatus.NOT_FOUND);
        }
    }

    // Create a new payment
    @PostMapping
    public ResponseEntity<Response> createPayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            PaymentDTO savedPayment = paymentMapper.convertToDTO(paymentService.createPayment(paymentMapper.convertToEntity(paymentDTO)));
            return new ResponseEntity<>(new Response("Payment initiated for Order ID: " + savedPayment.getOrderId(), savedPayment, false), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to initiate payment", e.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }

    // Update a payment
    @PutMapping("/{id}")
    public ResponseEntity<Response> updatePayment(@PathVariable Long id, @RequestBody PaymentDTO paymentDTO) {
        try {
            PaymentDTO updatedPayment = paymentMapper.convertToDTO(paymentService.updatePayment(id, paymentMapper.convertToEntity(paymentDTO)));
            return new ResponseEntity<>(new Response("Payment updated successfully", updatedPayment, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to update payment", e.getMessage(), true), HttpStatus.NOT_FOUND);
        }
    }

    // Delete a payment
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deletePayment(@PathVariable Long id) {
        try {
            paymentService.deletePayment(id);
            return new ResponseEntity<>(new Response("Payment deleted successfully", null, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to delete payment", e.getMessage(), true), HttpStatus.NOT_FOUND);
        }
    }
}
