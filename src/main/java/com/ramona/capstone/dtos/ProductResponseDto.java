package com.ramona.capstone.dtos;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
  private Long id;

  private String name;

  private BigDecimal basePrice;

  private String categoryName;

  private String brandName;

  private List<VariantDto> variants;
}
