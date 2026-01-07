package com.ramona.capstone.services;

import com.ramona.capstone.dtos.CategoryDto;
import com.ramona.capstone.exceptions.ResourceNotFoundException;
import com.ramona.capstone.mappers.CategoryMapper;
import com.ramona.capstone.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    public List<CategoryDto> getAllRootCategories() {
        return categoryRepository.findByParentIsNull().stream()
                .map(categoryMapper::toDto)
                .toList();
    }


    public List<CategoryDto> getSubCategories(Long parentId) {
        if(!categoryRepository.existsById(parentId)) {
            throw new ResourceNotFoundException("Category not found with ID:" + parentId);
        }
        return categoryRepository.findByParentId(parentId).stream()
                .map(categoryMapper::toDto)
                .toList();
    }
}
