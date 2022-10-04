package org.example.order.service.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.domain.event.DomainEvent;
import org.example.order.service.domain.entity.Order;

import java.time.ZonedDateTime;

@Getter
@AllArgsConstructor
public abstract class OrderEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime createdAt;
}
