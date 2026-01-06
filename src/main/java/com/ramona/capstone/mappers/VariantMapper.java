package com.ramona.capstone.mappers;

import com.ramona.capstone.dtos.VariantDto;
import com.ramona.capstone.entities.Variant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VariantMapper {
    @Mapping(source ="product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    VariantDto toDto(Variant variant);
    @Mapping(source = "productId", target = "product.id")
    Variant toEntity(VariantDto variantDto);
}
