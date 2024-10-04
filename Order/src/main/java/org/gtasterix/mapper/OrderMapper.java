package org.gtasterix.mapper;

import org.gtasterix.dto.OrderDTO;
import org.gtasterix.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    // Convert Order to OrderDTO
    public OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setProduct(order.getProduct());
        dto.setQuantity(order.getQuantity());
        return dto;
    }

    // Convert OrderDTO to Order
    public Order convertToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.getId()); // Set ID for updates
        order.setProduct(orderDTO.getProduct());
        order.setQuantity(orderDTO.getQuantity());
        return order;
    }
}
