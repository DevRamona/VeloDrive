package com.ramona.capstone.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
