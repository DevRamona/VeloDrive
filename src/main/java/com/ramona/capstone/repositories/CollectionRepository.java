package com.ramona.capstone.repositories;

import com.ramona.capstone.entities.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository <Collection, Long>{
    Optional<Collection> findByName(String name);

}
