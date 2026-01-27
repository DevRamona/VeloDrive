package com.ramona.capstone.controllers;

import com.ramona.capstone.dtos.VariantDto;
import com.ramona.capstone.dtos.VariantRequestDto;
import com.ramona.capstone.services.VariantService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  public ResponseEntity<VariantDto> getVariantById(
      @PathVariable Long variantId, @PathVariable Long productId) {
    return ResponseEntity.ok(variantService.getVariantById(productId, variantId));
  }

  @PostMapping
  public ResponseEntity<VariantDto> addVariant(
      @PathVariable Long productId, @Valid @RequestBody VariantRequestDto request) {
    return new ResponseEntity<>(
        variantService.createVariant(productId, request), HttpStatus.CREATED);
  }

  @PutMapping("/{variantId}")
  public ResponseEntity<VariantDto> updateExistingVariant(
      @PathVariable Long variantId,
      @PathVariable Long productId,
      @Valid @RequestBody VariantRequestDto request) {
    return new ResponseEntity<>(
        variantService.updateVariantById(productId, variantId, request), HttpStatus.OK);
  }

  @DeleteMapping("/{variantId}")
  public ResponseEntity<Void> deleteVariant(
      @PathVariable Long variantId, @PathVariable Long productId) {
    variantService.deleteVariant(productId, variantId);
    return ResponseEntity.noContent().build();
  }
}
