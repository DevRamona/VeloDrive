package com.ramona.capstone.services;

import com.ramona.capstone.dtos.ProductDto;
import com.ramona.capstone.entities.Brand;
import com.ramona.capstone.entities.Category;
import com.ramona.capstone.entities.Product;
import com.ramona.capstone.exceptions.ResourceNotFoundException;
import com.ramona.capstone.mappers.ProductMapper;
import com.ramona.capstone.repositories.BrandRepository;
import com.ramona.capstone.repositories.CategoryRepository;
import com.ramona.capstone.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

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
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Category category = categoryRepository.findByName(productDto.getCategoryName()).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + productDto.getCategoryName()));
        Brand brand = brandRepository.findByName(productDto.getBrandName()).orElseThrow(() -> new ResourceNotFoundException("Brand not found: " + productDto.getBrandName()));
        product.setCategory(category);
        product.setBrand(brand);
        return productMapper.toDto(productRepository.save(product));
    }
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with ID:" + id));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setBasePrice(productDto.getBasePrice());
        Category category = categoryRepository.findByName(productDto.getCategoryName()).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + productDto.getCategoryName()));
        Brand brand = brandRepository.findByName(productDto.getBrandName()).orElseThrow(() -> new ResourceNotFoundException("Brand not found: " + productDto.getBrandName()));
        product.setBrand(brand);
        product.setCategory(category);
        return productMapper.toDto(productRepository.save(product));

    }
}
