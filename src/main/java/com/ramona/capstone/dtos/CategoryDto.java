package com.ramona.capstone.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

  private Long id;

  @NotBlank(message = "Category name is required")
  private String name;

  @NotNull(message = "Parent category is required")
  private Long parentId;

  @NotBlank(message = "Parent category name is required")
  private String parentName;
}
