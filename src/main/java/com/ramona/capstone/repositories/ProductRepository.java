package com.ramona.capstone.repositories;

import com.ramona.capstone.entities.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByCategoryId(Long categoryId);

  List<Product> findByNameContainingIgnoreCase(String name);
}
