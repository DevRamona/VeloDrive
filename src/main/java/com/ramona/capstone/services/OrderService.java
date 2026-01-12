package com.ramona.capstone.services;

import com.ramona.capstone.dtos.OrderRequestDto;
import com.ramona.capstone.dtos.OrderResponseDto;
import com.ramona.capstone.entities.OrderItems;
import com.ramona.capstone.entities.Orders;
import com.ramona.capstone.entities.User;
import com.ramona.capstone.entities.Variant;
import com.ramona.capstone.exceptions.ResourceNotFoundException;
import com.ramona.capstone.mappers.OrderMapper;
import com.ramona.capstone.repositories.OrderItemsRepository;
import com.ramona.capstone.repositories.OrderRepository;
import com.ramona.capstone.repositories.UserRepository;
import com.ramona.capstone.repositories.VariantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final VariantRepository variantRepository;
    private final OrderMapper orderMapper;
    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto orderRequest) {
        User user = userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found with ID:" + orderRequest.getUserId()));
        Orders orders = new Orders();
        orders.setUser(user);
        orders.setPlacedAt(LocalDateTime.now());
        BigDecimal totalAmount = BigDecimal.ZERO;

        for(var itemRequest: orderRequest.getOrderItems()) {
            Variant variant = variantRepository.findById(itemRequest.getVariantId()).orElseThrow(() -> new ResourceNotFoundException("Product variant not found:" + itemRequest.getVariantId()));
            if(variant.getQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for:" + variant.getSku());
            }
            variant.setQuantity(variant.getQuantity() - itemRequest.getQuantity());
            OrderItems item = new OrderItems();
            item.setOrder(orders);
            item.setVariant(variant);
            item.setQuantity(itemRequest.getQuantity());
            item.setPriceAtPurchase(variant.getPrice());
            orders.getOrderItems().add(item);
            BigDecimal unitTotal = variant.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            totalAmount = totalAmount.add(unitTotal);

        }
        orders.setTotalAmount(totalAmount);
        Orders savedOrder =  orderRepository.save(orders);
        return orderMapper.toDto(savedOrder);


    }
    public List<OrderResponseDto> getUserOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

}
