package com.ramona.capstone.controllers;

import com.ramona.capstone.dtos.CategoryDto;
import com.ramona.capstone.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/roots")
    public ResponseEntity<List<CategoryDto>> getAllRootCategories(){
        return ResponseEntity.ok(categoryService.getAllRootCategories());
    }
    @GetMapping("/{parentId}/children")
    public ResponseEntity <List<CategoryDto>> getSubCategories(@PathVariable Long parentId){
        return ResponseEntity.ok(categoryService.getSubCategories(parentId));
    }
    @PostMapping
    public ResponseEntity<CategoryDto> addNewCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);

    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateExistingCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.updateCategory(id, categoryDto), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
