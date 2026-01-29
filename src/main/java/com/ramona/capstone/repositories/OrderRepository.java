package com.ramona.capstone.repositories;

import com.ramona.capstone.entities.Orders;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
  List<Orders> findByUserId(Long userId);

  List<Orders> findByUserEmail(String email);

  @Query("SELECT o FROM Orders o ORDER BY o.placedAt DESC")
  List<Orders> findMostRecentOrders();
}
