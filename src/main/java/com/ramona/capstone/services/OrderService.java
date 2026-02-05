package com.ramona.capstone.services;

import com.ramona.capstone.dtos.*;
import com.ramona.capstone.entities.OrderItems;
import com.ramona.capstone.entities.Orders;
import com.ramona.capstone.entities.User;
import com.ramona.capstone.entities.Variant;
import com.ramona.capstone.exceptions.BadRequestException;
import com.ramona.capstone.exceptions.InsufficientStockException;
import com.ramona.capstone.exceptions.ResourceNotFoundException;
import com.ramona.capstone.kafka.KafkaTopicConfig;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final VariantRepository variantRepository;
  private final OrderMapper orderMapper;
  private final KafkaTemplate<String, Object> kafkaTemplate;

  @Transactional
  public OrderResponseDto createOrder(OrderRequestDto orderRequest) {
    User user =
        userRepository
            .findById(orderRequest.getUserId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "User not found with ID:" + orderRequest.getUserId()));
    Orders order = new Orders();
    order.setUser(user);
    order.setPlacedAt(LocalDateTime.now());
    order.setStatus(com.ramona.capstone.models.OrderStatus.PENDING);
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

      OrderItems item = new OrderItems();
      item.setOrder(order);
      item.setVariant(variant);
      item.setQuantity(itemRequest.getQuantity());
      item.setPriceAtPurchase(variant.getPrice());
      order.getOrderItems().add(item);

      BigDecimal unitTotal =
          variant.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
      totalAmount = totalAmount.add(unitTotal);
    }

    order.setTotalAmount(totalAmount);
    Orders savedOrder = orderRepository.save(order);

    log.info("Created order: id = {}, status = PENDING", savedOrder.getId());
    return orderMapper.toDto(savedOrder);
  }

  @Transactional
  public CheckOutResponse checkout(Long id) {
    Orders order =
        orderRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID:" + id));

    if (order.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
      throw new BadRequestException("Order must have at least 1 item");
    }

    if (order.getStatus() != com.ramona.capstone.models.OrderStatus.PENDING) {
      throw new BadRequestException(
          "Order is already processed. Current status: " + order.getStatus());
    }

    order.setStatus(com.ramona.capstone.models.OrderStatus.PAID);
    Orders savedOrder = orderRepository.save(order);

    List<OrderItemEvent> orderItemEvents = mapToOrderItemEvents(savedOrder);

    OrderEvent orderEvent =
        OrderEvent.builder()
            .orderId(savedOrder.getId())
            .userId(savedOrder.getUser().getId())
            .placedAt(savedOrder.getPlacedAt())
            .status(savedOrder.getStatus())
            .orderItems(orderItemEvents)
            .build();

    kafkaTemplate.send(
        KafkaTopicConfig.ORDER_EVENTS_TOPIC, String.valueOf(savedOrder.getId()), orderEvent);

    log.info(
        "Order checked out and event published: orderId = {}, status = PAID", savedOrder.getId());

    return new CheckOutResponse("Order with id " + id + " has been checked out successfully");
  }

  public List<OrderResponseDto> getUserOrderHistory(Long userId) {
    return orderRepository.findByUserId(userId).stream()
        .map(orderMapper::toDto)
        .collect(Collectors.toList());
  }

  private List<OrderItemEvent> mapToOrderItemEvents(Orders order) {
    return order.getOrderItems().stream()
        .map(
            item ->
                OrderItemEvent.builder()
                    .variantId(item.getVariant().getId())
                    .sku(item.getVariant().getSku())
                    .quantity(item.getQuantity())
                    .build())
        .collect(Collectors.toList());
  }
}
