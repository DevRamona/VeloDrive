package com.ramona.capstone.controllers;

import com.ramona.capstone.dtos.OrderRequestDto;
import com.ramona.capstone.dtos.OrderResponseDto;
import com.ramona.capstone.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDto> createAnOrder(@Valid @RequestBody OrderRequestDto orderRequest) {
        OrderResponseDto response = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/history/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getUserHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getUserOrderHistory(userId));
    }

}
