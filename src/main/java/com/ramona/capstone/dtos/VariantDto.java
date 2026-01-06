package com.ramona.capstone.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VariantDto {
    private String color;
    private String sku;
    private BigDecimal price;
    private Integer quantity;
    private Long productId;
    private String productName;

}
