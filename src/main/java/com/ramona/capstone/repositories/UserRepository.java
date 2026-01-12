package com.ramona.capstone.repositories;

import com.ramona.capstone.entities.Orders;
import com.ramona.capstone.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    Boolean existsByEmail(String email);
    Boolean existsByName(String name);
}
