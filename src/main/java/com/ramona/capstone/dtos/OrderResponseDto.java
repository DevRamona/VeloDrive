package com.ramona.capstone.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
