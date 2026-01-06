package com.ramona.capstone.repositories;

import com.ramona.capstone.entities.Variant;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VariantRepository {
    Optional<Variant> findBySku(String sku);
    List<Variant> findByProductId(Long productId);
}
