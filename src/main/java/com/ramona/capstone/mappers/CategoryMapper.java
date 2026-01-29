package com.ramona.capstone.mappers;

import com.ramona.capstone.dtos.CategoryDto;
import com.ramona.capstone.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
  @Mapping(source = "parent.id", target = "parentId")
  @Mapping(source = "parent.name", target = "parentName")
  CategoryDto toDto(Category category);

  @Mapping(source = "parentId", target = "parent.id")
  Category toEntity(CategoryDto categoryDto);
}
