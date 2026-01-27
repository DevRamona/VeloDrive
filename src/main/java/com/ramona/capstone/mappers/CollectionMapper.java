package com.ramona.capstone.mappers;

import com.ramona.capstone.dtos.CollectionDto;
import com.ramona.capstone.dtos.CollectionRequestDto;
import com.ramona.capstone.entities.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CollectionMapper {
  CollectionDto toDto(Collection collection);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "products", ignore = true)
  Collection toEntity(CollectionRequestDto request);
}
