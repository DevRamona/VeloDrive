package com.ramona.capstone.repositories;

import com.ramona.capstone.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {
    List<Orders> findByUserId(Long userId);
    List<Orders> findByUserEmail(String email);
    @Query("SELECT o FROM Orders o ORDER BY o.placedAt DESC")
    List<Orders> findMostRecentOrders();

}
