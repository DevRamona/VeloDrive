package com.ramona.capstone.dtos;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class VariantPublicDto {
  private Long id;
  private String sku;
  private BigDecimal price;
  private String stockStatus;
}
