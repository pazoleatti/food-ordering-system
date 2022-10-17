package org.example.order.service.domain.ports.output.message.publisher.payment;

import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.order.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
