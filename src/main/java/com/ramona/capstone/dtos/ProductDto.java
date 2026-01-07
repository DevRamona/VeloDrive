package com.ramona.capstone.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    @NotNull(message = "Base price is required")
    private BigDecimal basePrice;
    private String categoryName;
    private String brandName;
}
