package org.example.restaurant.service.domain.ports.output.message.publisher;

import org.example.outbox.OutboxStatus;
import org.example.restaurant.service.domain.outbox.model.OrderOutboxMessage;

import java.util.function.BiConsumer;

public interface RestaurantApprovalResponseMessagePublisher {

    void publish(OrderOutboxMessage orderOutboxMessage,
                 BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback);
}
