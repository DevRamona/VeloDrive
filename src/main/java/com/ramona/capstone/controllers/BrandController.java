package com.ramona.capstone.controllers;

import com.ramona.capstone.dtos.BrandDto;
import com.ramona.capstone.dtos.BrandRequestDto;
import com.ramona.capstone.services.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;
    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllAvailableBrands());
    }
    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> getBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }
    @PostMapping
    public ResponseEntity<BrandDto> addNewBrand(@Valid @RequestBody BrandRequestDto request) {
        return new ResponseEntity<>(brandService.createBrand(request), HttpStatus.CREATED);
    }



}
