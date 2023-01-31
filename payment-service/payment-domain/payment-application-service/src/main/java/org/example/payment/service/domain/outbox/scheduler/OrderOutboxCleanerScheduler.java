package org.example.payment.service.domain.outbox.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.example.outbox.OutboxScheduler;
import org.example.outbox.OutboxStatus;
import org.example.payment.service.domain.outbox.model.OrderOutboxMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class OrderOutboxCleanerScheduler implements OutboxScheduler {
    private final OrderOutboxHelper orderOutboxHelper;

    public OrderOutboxCleanerScheduler(OrderOutboxHelper orderOutboxHelper) {
        this.orderOutboxHelper = orderOutboxHelper;
    }

    @Override
    @Transactional
    @Scheduled(cron = "@midnight")
    public void processingOutboxMessage() {
        Optional<List<OrderOutboxMessage>> outboxMessageResponse = orderOutboxHelper
                .getOrderOutboxMessageByOutboxStatus(OutboxStatus.COMPLETED);
        if (outboxMessageResponse.isPresent() && !outboxMessageResponse.get().isEmpty()) {
            List<OrderOutboxMessage> orderOutboxMessages = outboxMessageResponse.get();
            log.info("Received {} OrderOutboxMessage for Clean-up!", orderOutboxMessages.size());
            orderOutboxHelper.deleteOrderOutboxMessageByOutboxStatus(OutboxStatus.COMPLETED);
            log.info("Deleted {} OrderOutboxMessage!", orderOutboxMessages.size());
        }
    }
}
