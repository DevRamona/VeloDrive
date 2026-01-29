package com.ramona.capstone.controllers;

import com.ramona.capstone.dtos.CheckOutResponse;
import com.ramona.capstone.dtos.OrderRequestDto;
import com.ramona.capstone.dtos.OrderResponseDto;
import com.ramona.capstone.services.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderResponseDto> createAnOrder(
      @Valid @RequestBody OrderRequestDto orderRequest) {
    OrderResponseDto response = orderService.createOrder(orderRequest);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PostMapping("/{id}/checkout")
  public ResponseEntity<CheckOutResponse> checkout(@PathVariable Long id) {
    CheckOutResponse response = orderService.checkout(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/history/{userId}")
  public ResponseEntity<List<OrderResponseDto>> getUserHistory(@PathVariable Long userId) {
    return ResponseEntity.ok(orderService.getUserOrderHistory(userId));
  }
}
