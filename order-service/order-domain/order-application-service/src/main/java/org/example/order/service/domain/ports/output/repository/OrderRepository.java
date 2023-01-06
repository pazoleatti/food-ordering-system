package org.example.order.service.domain.ports.output.repository;

import org.example.domain.valueobject.OrderId;
import org.example.order.service.domain.entity.Order;
import org.example.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(OrderId orderId);
    Optional<Order> findByTrackingId(TrackingId trackingId);
}
