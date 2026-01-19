package com.ramona.capstone.mappers;

import com.ramona.capstone.dtos.ProductPublicDto;
import com.ramona.capstone.dtos.VariantPublicDto;
import com.ramona.capstone.entities.Product;
import com.ramona.capstone.entities.Variant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PublicMapper {
    @Mapping(source = "category.name", target ="categoryName")
    ProductPublicDto toPublicDto(Product product);
    @Mapping(source = "quantity", target = "stockStatus", qualifiedByName ="quantityToStatus" )
    VariantPublicDto toVariantPublicDto(Variant variant);
    @Named("quantityToStatus")
    default String quantityToStatus(Integer quantity) {
        if(quantity==null || quantity<=0) return "OUT_OF_STOCK";
        if(quantity<5) return "LOW_STOCK";
        return "IN_STOCK";
    }
}
