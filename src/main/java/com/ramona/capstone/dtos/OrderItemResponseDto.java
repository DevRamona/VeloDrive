package com.ramona.capstone.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {
    private Long variantId;
    private String productName;
    private String sku;
    private Integer quantity;
    private BigDecimal priceAtPurchase;
}
