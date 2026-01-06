package com.ramona.capstone.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private String categoryName;
    private String brandName;
}
