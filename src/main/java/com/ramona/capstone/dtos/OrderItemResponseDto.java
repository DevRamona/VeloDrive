package com.ramona.capstone.dtos;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
