package com.ramona.capstone.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long userId;
    private String userEmail;
    private BigDecimal totalAmount;
    private LocalDate placedAt;
    private List<OrderItemResponseDto> orderItems;
}
