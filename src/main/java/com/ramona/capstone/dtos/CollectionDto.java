package com.ramona.capstone.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectionDto {
    private Long id;
    private String name;
    private List<ProductDto> products;
}
