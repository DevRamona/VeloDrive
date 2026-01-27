package com.ramona.capstone.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
  @NotNull(message = "User ID is required")
  private Long userId;

  @NotEmpty(message = "Order must contain at least one item")
  @Valid
  private List<OrderItemRequestDto> orderItems;
}
