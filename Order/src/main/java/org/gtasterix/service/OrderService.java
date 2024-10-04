package org.gtasterix.service;

import org.gtasterix.model.Order;
import org.gtasterix.repositiory.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving orders: " + e.getMessage());
        }
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    public Order createOrder(Order order) {
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Error creating order: " + e.getMessage());
        }
    }

    public Order updateOrder(Long id, Order order) {
        try {
            getOrderById(id);  // Ensure order exists
            order.setId(id);
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new RuntimeException("Error updating order with ID: " + id + ". " + e.getMessage());
        }
    }

    public void deleteOrder(Long id) {
        try {
            getOrderById(id);  // Ensure order exists
            orderRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting order with ID: " + id + ". " + e.getMessage());
        }
    }
}
