package com.ramona.capstone.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@Setter
@Getter
@Table(name="orders")
public class Orders {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = false)
    private User user;
    @Column(name = "total_amount")
    private BigDecimal total_amount;
    @Column(name="placed_at")
    private LocalDateTime placedAt;
    @OneToMany(MappedBy="order", orphanRemoval = true)
    private List<OrderItems> items = new ArrayList<>();



}
