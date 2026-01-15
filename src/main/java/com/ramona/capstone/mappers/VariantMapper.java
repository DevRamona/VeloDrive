package com.ramona.capstone.mappers;

import com.ramona.capstone.dtos.VariantDto;
import com.ramona.capstone.dtos.VariantRequestDto;
import com.ramona.capstone.entities.Variant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VariantMapper {
    VariantDto toDto(Variant variant);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "id", ignore = true)
    Variant toEntity(VariantRequestDto request);

}
