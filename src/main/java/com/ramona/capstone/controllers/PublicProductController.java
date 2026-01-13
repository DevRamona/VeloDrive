package com.ramona.capstone.controllers;

import com.ramona.capstone.dtos.ProductPublicDto;
import com.ramona.capstone.mappers.PublicMapper;
import com.ramona.capstone.repositories.ProductRepository;
import com.ramona.capstone.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/products")
@RequiredArgsConstructor
public class PublicProductController {
    private final ProductRepository productRepository;
    private final PublicMapper publicMapper;
@GetMapping("/{id}")
    public ResponseEntity<ProductPublicDto>getProductDetails(@PathVariable Long id){
        return productRepository.findById(id)
                .map(product ->ResponseEntity.ok(publicMapper.toPublicDto(product)))
                .orElse(ResponseEntity.notFound().build());
    }
}
