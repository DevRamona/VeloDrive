package com.ramona.capstone.services;

import com.ramona.capstone.dtos.VariantDto;
import com.ramona.capstone.exceptions.ResourceNotFoundException;
import com.ramona.capstone.mappers.VariantMapper;
import com.ramona.capstone.repositories.ProductRepository;
import com.ramona.capstone.repositories.VariantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VariantService {
    private final VariantRepository variantRepository;
    private final VariantMapper variantMapper;
    private final ProductRepository productRepository;

    public VariantDto getVariantBySku(String sku) {
        return variantRepository.findBySku(sku)
                .map(variantMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("SKU not found: " + sku));
    }
    public List<VariantDto> getVariantsByProduct(Long productId) {
        if(!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with ID:" + productId);
        }
        return variantRepository.findByProductId(productId)
                .stream()
                .map(variantMapper::toDto)
                .toList();
    }
}
