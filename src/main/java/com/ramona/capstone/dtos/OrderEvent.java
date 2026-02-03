package com.ramona.capstone.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "PlacedAt timestamp is required")
    private LocalDateTime placedAt;

    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    private List<OrderItemEvent> orderItems;
}
