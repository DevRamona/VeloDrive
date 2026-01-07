package com.ramona.capstone.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantDto {
    private String color;
    private String sku;
    private BigDecimal price;
    private Integer quantity;
    private Long productId;
    private String productName;

}
