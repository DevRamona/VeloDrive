package com.ramona.capstone.controllers;

import com.ramona.capstone.dtos.UpdateStockDto;
import com.ramona.capstone.dtos.VariantDto;
import com.ramona.capstone.services.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {
  private final VariantService variantService;

  @PatchMapping("/sku/{sku}/stock")
  public ResponseEntity<VariantDto> updateStockBySku(
      @PathVariable String sku, @RequestBody UpdateStockDto updateDto) {
    return ResponseEntity.ok(variantService.updateStock(sku, updateDto));
  }
}
