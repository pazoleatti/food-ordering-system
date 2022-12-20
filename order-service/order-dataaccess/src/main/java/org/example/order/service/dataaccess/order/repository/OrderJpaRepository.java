package org.example.order.service.dataaccess.order.repository;

import org.example.order.service.dataaccess.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
    Optional<OrderEntity> findByTrackingId(UUID trackingId);
}
