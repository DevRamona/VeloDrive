package com.ramona.capstone.controllers;

import com.ramona.capstone.dtos.ApiResponse;
import com.ramona.capstone.dtos.CollectionDto;
import com.ramona.capstone.dtos.CollectionRequestDto;
import com.ramona.capstone.services.CollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collections")
public class CollectionController {
    private final CollectionService collectionService;
    @PostMapping
    public ResponseEntity<CollectionDto> addNewCollection(@Valid @RequestBody CollectionRequestDto request) {
        return new ResponseEntity<>(collectionService.createCollection(request),  HttpStatus.CREATED);
    }

    @PostMapping("/{collectionId}/products/{productId}")
    public ResponseEntity <ApiResponse<String>>createProductToCollection(@PathVariable Long collectionId, @PathVariable Long productId) {
     collectionService.addProductToCollection(collectionId, productId);
        return ResponseEntity.ok(new ApiResponse<>("Product has been successfully added to the collection.", productId));
    }
}
