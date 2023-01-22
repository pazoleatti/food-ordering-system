package org.example.order.service.domain.outbox.scheduler.approval;

import lombok.extern.slf4j.Slf4j;
import org.example.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import org.example.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import org.example.order.service.domain.ports.output.message.publisher.restaurantapproval.RestaurantApprovalMessagePublisher;
import org.example.outbox.OutboxScheduler;
import org.example.outbox.OutboxStatus;
import org.example.saga.SagaStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ApprovalOutboxScheduler implements OutboxScheduler {
    private final ApprovalOutboxHelper approvalOutboxHelper;
    private final RestaurantApprovalMessagePublisher restaurantApprovalMessagePublisher;

    public ApprovalOutboxScheduler(ApprovalOutboxHelper approvalOutboxHelper, RestaurantApprovalMessagePublisher restaurantApprovalMessagePublisher) {
        this.approvalOutboxHelper = approvalOutboxHelper;
        this.restaurantApprovalMessagePublisher = restaurantApprovalMessagePublisher;
    }

    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${order-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${order-service.outbox-scheduler-initial-delay}")
    public void processingOutboxMessage() {
        Optional<List<OrderApprovalOutboxMessage>> outboxMessageResponse = approvalOutboxHelper.
                getApprovalOutBoxMessageByOutboxStatusAndSagaStatus(OutboxStatus.STARTED, SagaStatus.PROCESSING);
        if (outboxMessageResponse.isPresent() && !outboxMessageResponse.get().isEmpty()) {
            List<OrderApprovalOutboxMessage> outboxMessages = outboxMessageResponse.get();
            log.info("Received {} OrderApprovalOutboxMessage with ids: {}, sending to message bus!",
                    outboxMessages.size(),
                    outboxMessages.stream().map(outboxMessage ->
                            outboxMessage.getId().toString()).collect(Collectors.joining(",")));
            outboxMessages.forEach(outboxMessage ->
                    restaurantApprovalMessagePublisher.publish(outboxMessage, this::updateOutboxStatus));
            log.info("{} OrderApprovalOutboxMessage sent to message bus!", outboxMessages.size());
        }
    }

    private void updateOutboxStatus(OrderApprovalOutboxMessage orderApprovalOutboxMessage, OutboxStatus outboxStatus) {
        orderApprovalOutboxMessage.setOutboxStatus(outboxStatus);
        approvalOutboxHelper.save(orderApprovalOutboxMessage);
        log.info("OrderApprovalOutboxMessage is updated with outbox status: {}", outboxStatus.name());
    }
}
