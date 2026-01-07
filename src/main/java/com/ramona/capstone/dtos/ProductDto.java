package com.ramona.capstone.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    @NotBlank(message = "Product name is required")
    @Size(min =2, max = 50, message = "Product name must be between 2 and 50 characters")
    private String name;
    @NotBlank(message = "Product description is required")
    private String description;
    @NotNull(message = "Base price is required")
    @Positive(message = "Base price must be positive")
    private BigDecimal basePrice;
    @NotBlank(message = "Category name is required")
    private String categoryName;
    @NotBlank(message = "Brand name is required")
    private String brandName;
}
