package com.ramona.capstone.repositories;

import com.ramona.capstone.entities.Variant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {
  Optional<Variant> findBySku(String sku);

  List<Variant> findByProductId(Long productId);

  boolean existsBySku(String sku);
}
