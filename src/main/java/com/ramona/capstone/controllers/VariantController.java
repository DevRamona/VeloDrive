package com.ramona.capstone.controllers;

import com.ramona.capstone.dtos.VariantDto;
import com.ramona.capstone.services.VariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product/{productId}/variants")
@RequiredArgsConstructor
public class VariantController {
    private final VariantService variantService;
//    @GetMapping("/sku/{sku}")
//    public ResponseEntity<VariantDto> getVariantBySku(@PathVariable String sku) {
//        return ResponseEntity.ok(variantService.getVariantBySku(sku));
//    }
    @GetMapping
    public ResponseEntity<List<VariantDto>> getVariantsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(variantService.getVariantsByProduct(productId));
    }
    @GetMapping("/{variantId}")
    public ResponseEntity<VariantDto> getVariantById(@PathVariable Long variantId, @PathVariable Long productId) {
        return ResponseEntity.ok(variantService.getVariantById(productId, variantId));
    }
    @PostMapping
    public ResponseEntity<VariantDto> addVariant( @PathVariable Long productId,@Valid @RequestBody VariantDto variantDto) {
        return new ResponseEntity<>(variantService.createVariant(productId,variantDto), HttpStatus.CREATED);
    }
    @PutMapping("/{variantId}")
    public ResponseEntity<VariantDto> updateVariant(@PathVariable Long variantId,@PathVariable Long productId,@Valid @RequestBody VariantDto variantDto) {
        return new ResponseEntity<>(variantService.updateVariantById(productId,variantId,variantDto), HttpStatus.OK);
    }
    @DeleteMapping("/{variantId}")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long variantId,@PathVariable Long productId) {
        variantService.deleteVariant(productId,variantId);
        return ResponseEntity.noContent().build();
    }

}
