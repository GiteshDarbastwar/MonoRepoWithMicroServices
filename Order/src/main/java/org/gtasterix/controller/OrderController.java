package org.gtasterix.controller;

import org.gtasterix.client.PaymentServiceClient;
import org.gtasterix.dto.OrderDTO;
import org.gtasterix.mapper.OrderMapper;
import org.gtasterix.model.Order;
import org.gtasterix.model.PaymentRequest;
import org.gtasterix.repositiory.OrderRepository;
import org.gtasterix.service.OrderService;
import org.gtasterix.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentServiceClient paymentServiceClient;

    @Autowired
    private OrderMapper orderMapper;

    // Fetch all orders
    @GetMapping
    public ResponseEntity<Response> getAllOrders() {
        try {
            List<OrderDTO> orders = orderService.getAllOrders()
                    .stream()
                    .map(orderMapper::convertToDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(new Response("Success", orders, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to fetch orders", e.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Fetch order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Response> getOrderById(@PathVariable Long id) {
        try {
            OrderDTO order = orderMapper.convertToDTO(orderService.getOrderById(id));
            return new ResponseEntity<>(new Response("Success", order, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to fetch order", e.getMessage(), true), HttpStatus.NOT_FOUND);
        }
    }

    // Create a new order
    @PostMapping
    public ResponseEntity<Response> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            Order savedOrder = orderService.createOrder(orderMapper.convertToEntity(orderDTO));

            // Trigger payment
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setOrderId(savedOrder.getId());
            paymentRequest.setAmount(100.0);  // This should be dynamic based on your logic
            paymentServiceClient.initiatePayment(paymentRequest);

            return new ResponseEntity<>(new Response("Order created with ID: " + savedOrder.getId(), savedOrder, false), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to create order", e.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }

    // Update an order
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
        try {
            Order updatedOrder = orderService.updateOrder(id, orderMapper.convertToEntity(orderDTO));
            return new ResponseEntity<>(new Response("Order updated successfully", updatedOrder, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to update order", e.getMessage(), true), HttpStatus.NOT_FOUND);
        }
    }

    // Delete an order
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return new ResponseEntity<>(new Response("Order deleted successfully", null, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to delete order", e.getMessage(), true), HttpStatus.NOT_FOUND);
        }
    }
}
