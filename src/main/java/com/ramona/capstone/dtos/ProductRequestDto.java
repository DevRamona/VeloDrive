package com.ramona.capstone.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
  @NotBlank(message = "Product name is required")
  private String name;

  @NotBlank(message = "Product description is required")
  private String description;

  @NotNull(message = "Base price is required")
  @Positive(message = "Base price must be positive")
  private BigDecimal basePrice;

  @NotBlank(message = "Category name is required")
  private String categoryName;

  @NotBlank(message = "Brand name is required")
  private String brandName;

  @NotNull(message = "At least one variant is required")
  @Valid
  private List<VariantRequestDto> variants;
}
