package com.ramona.capstone.dtos;

import com.ramona.capstone.models.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {

  @NotNull(message = "Order ID is required")
  private Long orderId;

  @NotNull(message = "User ID is required")
  private Long userId;

  @NotNull(message = "PlacedAt timestamp is required")
  private LocalDateTime placedAt;

  @NotNull(message = "Order status is required")
  private OrderStatus status;

  @NotEmpty(message = "Order must contain at least one item")
  @Valid
  private List<OrderItemEvent> orderItems;
}
