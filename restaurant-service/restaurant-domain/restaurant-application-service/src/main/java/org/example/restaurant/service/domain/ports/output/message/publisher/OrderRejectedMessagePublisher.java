package org.example.restaurant.service.domain.ports.output.message.publisher;

import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.restaurant.service.domain.event.OrderRejectedEvent;

public interface OrderRejectedMessagePublisher extends DomainEventPublisher<OrderRejectedEvent> {
}
