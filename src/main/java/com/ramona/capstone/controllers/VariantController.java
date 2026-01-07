package com.ramona.capstone.controllers;

import com.ramona.capstone.dtos.VariantDto;
import com.ramona.capstone.services.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/variants")
@RequiredArgsConstructor
public class VariantController {
    private final VariantService variantService;
    @GetMapping("/sku/{sku}")
    public ResponseEntity<VariantDto> getVariantBySku(@PathVariable String sku) {
        return ResponseEntity.ok(variantService.getVariantBySku(sku));
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<VariantDto>> getVariantsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(variantService.getVariantsByProduct(productId));
    }
}
