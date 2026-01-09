package com.ramona.capstone.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantDto {
    private Long id;
    @NotBlank(message = "Color is required")
    private String color;

    @NotBlank(message = "Sku is required")
    private String sku;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    @NotBlank(message = "Fuel type is required")
    private String fuelType;

    private Long productId;

    private String productName;


}
