package com.ramona.capstone.services;

import com.ramona.capstone.dtos.CollectionDto;
import com.ramona.capstone.dtos.CollectionRequestDto;
import com.ramona.capstone.entities.Collection;
import com.ramona.capstone.entities.Product;
import com.ramona.capstone.mappers.CollectionMapper;
import com.ramona.capstone.repositories.CollectionRepository;
import com.ramona.capstone.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CollectionService {
  private final CollectionRepository collectionRepository;
  private final ProductRepository productRepository;
  private final CollectionMapper collectionMapper;

  @Transactional
  public void addProductToCollection(Long collectionId, Long productId) {
    Collection collection =
        collectionRepository
            .findById(collectionId)
            .orElseThrow(() -> new IllegalArgumentException("Collection not found"));
    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    if (!collection.getProducts().contains(product)) {
      collection.getProducts().add(product);
    }
    collectionRepository.save(collection);
  }

  @Transactional
  public CollectionDto createCollection(CollectionRequestDto request) {
    collectionRepository
        .findByName(request.getName())
        .ifPresent(
            collection -> {
              throw new IllegalArgumentException("Collection already exists");
            });
    Collection collection = collectionMapper.toEntity(request);
    return collectionMapper.toDto(collectionRepository.save(collection));
  }
}
