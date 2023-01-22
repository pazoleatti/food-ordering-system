package org.example.order.service.domain.ports.output.message.publisher.payment;

import org.example.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import org.example.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface PaymentRequestMessagePublisher {
    void publish(OrderPaymentOutboxMessage orderPaymentOutboxMessage,
                 BiConsumer<OrderPaymentOutboxMessage, OutboxStatus> outboxCallback);
}
