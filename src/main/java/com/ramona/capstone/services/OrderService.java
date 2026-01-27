package com.ramona.capstone.services;

import com.ramona.capstone.dtos.CheckOutResponse;
import com.ramona.capstone.dtos.OrderRequestDto;
import com.ramona.capstone.dtos.OrderResponseDto;
import com.ramona.capstone.entities.OrderItems;
import com.ramona.capstone.entities.Orders;
import com.ramona.capstone.entities.User;
import com.ramona.capstone.entities.Variant;
import com.ramona.capstone.exceptions.BadRequestException;
import com.ramona.capstone.exceptions.InsufficientStockException;
import com.ramona.capstone.exceptions.ResourceNotFoundException;
import com.ramona.capstone.mappers.OrderMapper;
import com.ramona.capstone.repositories.OrderRepository;
import com.ramona.capstone.repositories.UserRepository;
import com.ramona.capstone.repositories.VariantRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final VariantRepository variantRepository;
  private final OrderMapper orderMapper;

  @Transactional
  public OrderResponseDto createOrder(OrderRequestDto orderRequest) {
    User user =
        userRepository
            .findById(orderRequest.getUserId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "User not found with ID:" + orderRequest.getUserId()));
    Orders orders = new Orders();
    orders.setUser(user);
    orders.setPlacedAt(LocalDateTime.now());
    BigDecimal totalAmount = BigDecimal.ZERO;

    for (var itemRequest : orderRequest.getOrderItems()) {
      Variant variant =
          variantRepository
              .findById(itemRequest.getVariantId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Product variant not found:" + itemRequest.getVariantId()));
      if (variant.getQuantity() < itemRequest.getQuantity()) {
        throw new InsufficientStockException("Insufficient stock for:" + variant.getSku());
      }
      variant.setQuantity(variant.getQuantity() - itemRequest.getQuantity());
      OrderItems item = new OrderItems();
      item.setOrder(orders);
      item.setVariant(variant);
      item.setQuantity(itemRequest.getQuantity());
      item.setPriceAtPurchase(variant.getPrice());
      orders.getOrderItems().add(item);
      BigDecimal unitTotal =
          variant.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
      totalAmount = totalAmount.add(unitTotal);
    }
    orders.setTotalAmount(totalAmount);
    Orders savedOrder = orderRepository.save(orders);
    return orderMapper.toDto(savedOrder);
  }

  public CheckOutResponse checkout(Long id) {
    var order =
        orderRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID:" + id));
    if (order.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new BadRequestException("Order must have at least 1 item");
    }
    orderRepository.save(order);
    return new CheckOutResponse("An order with id" + id + " has been checked out successfully");
  }

  public List<OrderResponseDto> getUserOrderHistory(Long userId) {
    return orderRepository.findByUserId(userId).stream()
        .map(orderMapper::toDto)
        .collect(Collectors.toList());
  }
}
