package org.example.order.service.domain.ports.output.message.publisher.restaurantapproval;

import org.example.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import org.example.outbox.OutboxStatus;

import java.util.function.BiConsumer;

public interface RestaurantApprovalMessagePublisher {
    void publish(OrderApprovalOutboxMessage orderApprovalOutboxMessage,
                 BiConsumer<OrderApprovalOutboxMessage, OutboxStatus> outboxCallback);
}
