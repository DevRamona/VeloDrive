package com.ramona.capstone.services;

import com.ramona.capstone.dtos.ProductDto;
import com.ramona.capstone.entities.Brand;
import com.ramona.capstone.entities.Category;
import com.ramona.capstone.entities.Product;
import com.ramona.capstone.entities.Variant;
import com.ramona.capstone.exceptions.DuplicateSkuException;
import com.ramona.capstone.exceptions.ResourceNotFoundException;
import com.ramona.capstone.mappers.ProductMapper;
import com.ramona.capstone.repositories.BrandRepository;
import com.ramona.capstone.repositories.CategoryRepository;
import com.ramona.capstone.repositories.ProductRepository;
import com.ramona.capstone.repositories.VariantRepository;
import jakarta.transaction.Transactional;
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
    private final VariantRepository variantRepository;

    public List<ProductDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(productMapper::toDto)
                .toList();
    }
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();
    }
    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID:" + id));
    }
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        Category category = categoryRepository.findByName(productDto.getCategoryName()).orElseThrow(() -> new ResourceNotFoundException("Category not found: " + productDto.getCategoryName()));
        if(!category.getChildren().isEmpty()) {
            throw new IllegalArgumentException("Products must be assigned to a category");
        }
        Product product = productMapper.toEntity(productDto);
        product.setCategory(category);
        Brand brand = brandRepository.findByName(productDto.getBrandName()).orElseThrow(() -> new ResourceNotFoundException("Brand not found: " + productDto.getBrandName()));
        product.setBrand(brand);

        if(product.getVariants()!=null){
            for(Variant variant: product.getVariants()){
                if(variantRepository.existsBySku(variant.getSku())){
                    throw new DuplicateSkuException("SKU" +  variant.getSku() + " already exists");
                }
                variant.setProduct(product);
            }
        }

        return productMapper.toDto(productRepository.save(product));
    }
    @Transactional
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
    @Transactional
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with ID:" + id);
        }
        productRepository.deleteById(id);
    }

}
