package com.ramona.capstone.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VariantPublicDto {
    private Long id;
    private String sku ;
    private BigDecimal price;
    private String stockStatus;
}
