package com.ramona.capstone.repositories;

import com.ramona.capstone.entities.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
  Optional<Collection> findByName(String name);
}
