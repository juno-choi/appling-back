package com.simol.appling.order.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simol.appling.order.domain.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    
}
