package com.ramona.capstone.services;

import com.ramona.capstone.dtos.ProductDto;
import com.ramona.capstone.exceptions.ResourceNotFoundException;
import com.ramona.capstone.mappers.ProductMapper;
import com.ramona.capstone.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID:" + id));
    }
}
