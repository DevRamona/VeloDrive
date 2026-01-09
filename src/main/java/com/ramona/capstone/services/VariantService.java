package com.ramona.capstone.services;

import com.ramona.capstone.dtos.VariantDto;
import com.ramona.capstone.dtos.VariantRequestDto;
import com.ramona.capstone.entities.Product;
import com.ramona.capstone.entities.Variant;
import com.ramona.capstone.exceptions.ResourceNotFoundException;
import com.ramona.capstone.mappers.VariantMapper;
import com.ramona.capstone.repositories.ProductRepository;
import com.ramona.capstone.repositories.VariantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.ramona.capstone.dtos.UpdateStockDto;


import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
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
    @Transactional
    public VariantDto createVariant(Long productId, VariantRequestDto request ) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Cannot add variant. Product not found with ID:" + productId));
        Variant variant = variantMapper.toEntity(request);
        variant.setProduct(product);
        return variantMapper.toDto(variantRepository.save(variant));
    }


    public VariantDto getVariantById(Long productId, Long variantId) {
        return variantRepository.findById(variantId)
                .map(variantMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found with ID:" + variantId));
    }
    @Transactional
    public VariantDto updateVariantById(Long productId, Long variantId, VariantRequestDto request) {
        Variant variant = variantRepository.findById(variantId).orElseThrow(() -> new ResourceNotFoundException("Variant not found with ID:" + variantId));
        variant.setColor(request.getColor());
        variant.setPrice(request.getPrice());
        variant.setSku(request.getSku());
        variant.setQuantity(request.getQuantity());
        variant.setFuelType(request.getFuelType());
        return variantMapper.toDto(variantRepository.save(variant));
    }
    @Transactional
    public void deleteVariant(Long productId, Long variantId) {
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found"));

        if (!variant.getProduct().getId().equals(productId)) {
            throw new IllegalArgumentException("Cannot delete: Variant belongs to a different product");
        }

        variantRepository.delete(variant);
    }
    @Transactional
    public VariantDto updateStock(String sku, UpdateStockDto stockUpdate) {
        Variant variant = variantRepository.findBySku(sku).orElseThrow(() -> new ResourceNotFoundException("Variant with SKU" + sku + "not found"));
        variant.setQuantity(stockUpdate.getQuantity());
        return variantMapper.toDto(variantRepository.save(variant));
    }
}
