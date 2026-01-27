package com.ramona.capstone.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequestDto {
  @NotNull(message = "Variant ID is required")
  private Long variantId;

  @NotNull(message = "Quantity is required ")
  @Positive(message = "Quantity must be greater than zero")
  private Integer quantity;
}
