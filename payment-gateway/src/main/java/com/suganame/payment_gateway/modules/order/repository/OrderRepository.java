package com.suganame.payment_gateway.modules.order.repository;

import com.suganame.payment_gateway.modules.order.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
