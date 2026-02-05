package com.ramona.capstone.mappers;

import com.ramona.capstone.dtos.OrderItemResponseDto;
import com.ramona.capstone.dtos.OrderResponseDto;
import com.ramona.capstone.entities.OrderItems;
import com.ramona.capstone.entities.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
  @Mapping(source = "user.id", target = "userId")
  @Mapping(source = "user.email", target = "userEmail")
  @Mapping(source = "id", target = "id")
  @Mapping(source = "status", target = "status")
  OrderResponseDto toDto(Orders orders);

  @Mapping(source = "variant.id", target = "variantId")
  @Mapping(source = "variant.sku", target = "sku")
  @Mapping(source = "variant.product.name", target = "productName")
  OrderItemResponseDto toDto(OrderItems item);
}
