package org.example.payment.service.domain.ports.output.message.publisher;

import org.example.outbox.OutboxStatus;
import org.example.payment.service.domain.outbox.model.OrderOutboxMessage;

import java.util.function.BiConsumer;

public interface PaymentResponseMessagePublisher {
    void publish(OrderOutboxMessage outboxMessage, BiConsumer<OrderOutboxMessage, OutboxStatus> outboxCallback);
}
