package com.ramona.capstone.mappers;

import com.ramona.capstone.dtos.ProductRequestDto;
import com.ramona.capstone.dtos.ProductResponseDto;
import com.ramona.capstone.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  @Mapping(source = "category.name", target = "categoryName")
  @Mapping(source = "brand.name", target = "brandName")
  ProductResponseDto toDto(Product product);

  @Mapping( target = "id", ignore = true)
  @Mapping( target = "brand", ignore = true)
  @Mapping(target = "category", ignore = true)
  Product toEntity(ProductRequestDto productDto);
}
