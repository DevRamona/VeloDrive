package com.ramona.capstone.repositories;

import com.ramona.capstone.entities.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  List<Category> findByParentIsNull();

  List<Category> findByParentId(Long parentId);

  Optional<Category> findByName(String name);
}
