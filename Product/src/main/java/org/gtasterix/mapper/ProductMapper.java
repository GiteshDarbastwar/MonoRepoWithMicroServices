package org.gtasterix.mapper;

import org.gtasterix.dto.ProductDTO;
import org.gtasterix.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    // Convert Product to ProductDTO
    public ProductDTO convertToDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        return dto;
    }

    // Convert ProductDTO to Product
    public Product convertToEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productDTO.getId()); // If you want to set the ID for updates
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        return product;
    }
}
