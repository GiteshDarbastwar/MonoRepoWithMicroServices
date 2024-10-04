package org.gtasterix.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private String product;
    private int quantity;
}
