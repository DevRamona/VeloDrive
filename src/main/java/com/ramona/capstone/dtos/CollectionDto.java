package com.ramona.capstone.dtos;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDto {
  private String name;
  private List<ProductResponseDto> product = new ArrayList<>();
}
