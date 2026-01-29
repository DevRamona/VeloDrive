package com.ramona.capstone.mappers;

import com.ramona.capstone.dtos.ProductDto;
import com.ramona.capstone.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  @Mapping(source = "category.name", target = "categoryName")
  @Mapping(source = "brand.name", target = "brandName")
  ProductDto toDto(Product product);

  @Mapping(source = "categoryName", target = "category.name")
  @Mapping(source = "brandName", target = "brand.name")
  Product toEntity(ProductDto productDto);
}
