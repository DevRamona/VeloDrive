package com.ramona.capstone.services;

import com.ramona.capstone.dtos.CategoryDto;
import com.ramona.capstone.entities.Category;
import com.ramona.capstone.exceptions.ResourceNotFoundException;
import com.ramona.capstone.mappers.CategoryMapper;
import com.ramona.capstone.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
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
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);

        if(categoryDto.getParentId() != null ) {
             Category parent = categoryRepository.findById(categoryDto.getParentId()).orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
            category.setParent(parent);

        }
         Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(categoryRepository.save(savedCategory));

    }
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID:" + id));
        category.setName(categoryDto.getName());
        return categoryMapper.toDto(categoryRepository.save(category));
    }
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with ID:" + id));
        categoryRepository.deleteById(id);
    }
}
