package com.ramona.capstone.controllers;

import com.ramona.capstone.dtos.CategoryDto;
import com.ramona.capstone.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
