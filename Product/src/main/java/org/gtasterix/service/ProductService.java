package org.gtasterix.service;

import org.gtasterix.dto.ProductDTO;
import org.gtasterix.exception.ProductNotFoundException;
import org.gtasterix.mapper.ProductMapper; // Import the mapper
import org.gtasterix.model.Product;

import org.gtasterix.repositiory.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper; // Inject the ProductMapper

    public List<ProductDTO> getAllProducts() {
        try {
            return productRepository.findAll().stream()
                    .map(productMapper::convertToDTO) // Use mapper for conversion
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products: " + e.getMessage());
        }
    }

    public ProductDTO getProductById(Long id) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found for ID: " + id));
            return productMapper.convertToDTO(product); // Use mapper for conversion
        } catch (Exception e) {
            throw new RuntimeException("Error fetching product by ID: " + e.getMessage());
        }
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        try {
            Product product = productMapper.convertToEntity(productDTO); // Use mapper for conversion
            Product savedProduct = productRepository.save(product);
            return productMapper.convertToDTO(savedProduct); // Use mapper for conversion
        } catch (Exception e) {
            throw new RuntimeException("Failed to create product: " + e.getMessage());
        }
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        try {
            Product existingProduct = productRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found for ID: " + id));
            existingProduct.setName(productDTO.getName());
            existingProduct.setPrice(productDTO.getPrice());
            Product updatedProduct = productRepository.save(existingProduct);
            return productMapper.convertToDTO(updatedProduct); // Use mapper for conversion
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product: " + e.getMessage());
        }
    }

    public void deleteProduct(Long id) {
        try {
            if (!productRepository.existsById(id)) {
                throw new ProductNotFoundException("Product not found for ID: " + id);
            }
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete product: " + e.getMessage());
        }
    }
}
