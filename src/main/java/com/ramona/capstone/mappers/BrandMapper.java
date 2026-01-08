package com.ramona.capstone.mappers;

import com.ramona.capstone.dtos.BrandDto;
import com.ramona.capstone.dtos.BrandRequestDto;
import com.ramona.capstone.entities.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandDto toDto(Brand brand);
    Brand toEntity(BrandRequestDto request);

}
