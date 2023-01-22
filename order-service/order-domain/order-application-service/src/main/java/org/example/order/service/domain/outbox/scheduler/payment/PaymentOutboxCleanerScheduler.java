package org.example.order.service.domain.outbox.scheduler.payment;

import lombok.extern.slf4j.Slf4j;
import org.example.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import org.example.outbox.OutboxScheduler;
import org.example.outbox.OutboxStatus;
import org.example.saga.SagaStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class PaymentOutboxCleanerScheduler implements OutboxScheduler {
    private final PaymentOutboxHelper paymentOutboxHelper;

    public PaymentOutboxCleanerScheduler(PaymentOutboxHelper paymentOutboxHelper) {
        this.paymentOutboxHelper = paymentOutboxHelper;
    }


    @Override
    @Scheduled(cron = "@midnight")
    public void processingOutboxMessage() {
        Optional<List<OrderPaymentOutboxMessage>> outboxMessageResponse = paymentOutboxHelper
                .getPaymentOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus.COMPLETED,
                SagaStatus.SUCCEEDED,
                SagaStatus.FAILED,
                SagaStatus.COMPENSATED);
        if (outboxMessageResponse.isPresent()) {
            List<OrderPaymentOutboxMessage> outboxMessages = outboxMessageResponse.get();
            log.info("Received {} OrderPaymentOutboxMessage for cleanup. The payloads: {}",
                    outboxMessages.size(),
                    outboxMessages.stream().map(OrderPaymentOutboxMessage::getPayload)
                            .collect(Collectors.joining("\n")));
            paymentOutboxHelper.deletePaymentOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus.COMPLETED,
                    SagaStatus.SUCCEEDED,
                    SagaStatus.FAILED,
                    SagaStatus.COMPENSATED);
            log.info("{} OrderPaymentOutboxMessage deleted!", outboxMessages.size());
        }
    }
}
