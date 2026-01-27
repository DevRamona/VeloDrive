package com.ramona.capstone.dtos;

import java.util.List;
import lombok.Data;

@Data
public class ProductPublicDto {
  private String name;
  private String categoryName;
  private List<VariantPublicDto> variants;
}
