package com.ramona.capstone.controllers;

import com.ramona.capstone.dtos.ProductDto;
import com.ramona.capstone.services.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @GetMapping("/category/{categoryId}")
  public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Long categoryId) {
    return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
  }

  @GetMapping
  public ResponseEntity<List<ProductDto>> getAllProducts() {
    return ResponseEntity.ok(productService.getAllProducts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
    return ResponseEntity.ok(productService.getProductById(id));
  }

  @PostMapping
  public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto) {
    return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProductDto> updateExistingProduct(
      @PathVariable Long id, @Valid @RequestBody ProductDto productDto) {
    return new ResponseEntity<>(productService.updateProduct(id, productDto), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }
}
