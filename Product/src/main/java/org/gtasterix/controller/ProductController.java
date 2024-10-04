package org.gtasterix.controller;

import org.gtasterix.dto.ProductDTO;
import org.gtasterix.service.ProductService;
import org.gtasterix.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Response> getAllProducts() {
        try {
            List<ProductDTO> products = productService.getAllProducts();
            return new ResponseEntity<>(new Response("Success", products, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to fetch products", e.getMessage(), true), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getProductById(@PathVariable Long id) {
        try {
            ProductDTO product = productService.getProductById(id);
            return new ResponseEntity<>(new Response("Success", product, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to fetch product", e.getMessage(), true), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Response> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO createdProduct = productService.createProduct(productDTO);
            return new ResponseEntity<>(new Response("Product created successfully", createdProduct, false), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to create product", e.getMessage(), true), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
            return new ResponseEntity<>(new Response("Product updated successfully", updatedProduct, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to update product", e.getMessage(), true), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return new ResponseEntity<>(new Response("Product deleted successfully", null, false), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response("Failed to delete product", e.getMessage(), true), HttpStatus.NOT_FOUND);
        }
    }
}
