package com.ramona.capstone.services;

import com.ramona.capstone.dtos.OrderEvent;
import com.ramona.capstone.dtos.OrderItemEvent;
import com.ramona.capstone.entities.Variant;
import com.ramona.capstone.exceptions.InsufficientStockException;
import com.ramona.capstone.exceptions.ResourceNotFoundException;
import com.ramona.capstone.repositories.VariantRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@AllArgsConstructor
public class InventoryConsumerService {
    private final VariantRepository variantRepository;



    @KafkaListener(
            topics = "order-events",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )

@Transactional
public void consumeOrderEvent(
        @Payload OrderEvent event,
        Acknowledgment acknowledgment,
        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
        @Header(KafkaHeaders.OFFSET) long offset) {

        log.info("Received order event: id = {}, partition = {}, offset = {}", event.getOrderId(), partition, offset );
        try {
            for(OrderItemEvent item : event.getOrderItems()) {
                updateInventory(item);
            }
            acknowledgment.acknowledge();
            log.info("Processed order event: id = {}", event.getOrderId());
        }catch(Exception exception) {
            log.error("Failed to process order event: id = {}, error = {}", event.getOrderId(), exception.getMessage(), exception);
            throw new RuntimeException("Failed to process order event:" + event.getOrderId(), exception);
        }
    }

    private void updateInventory(OrderItemEvent item) {
        Variant variant = variantRepository.findBySku(item.getSku())
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found with SKU: " + item.getSku()));
        if(variant.getQuantity() < item.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock for order item: " + item.getSku());
        }
        variant.setQuantity(variant.getQuantity() - item.getQuantity());
        variantRepository.save(variant);
        log.info("Updated inventory: sku = {}, delta = {}, remaining = {}", item.getSku(), item.getQuantity(), variant.getQuantity());
    }

}
