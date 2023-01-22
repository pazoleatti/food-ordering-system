package org.example.order.service.domain.outbox.scheduler.approval;

import lombok.extern.slf4j.Slf4j;
import org.example.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import org.example.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
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
public class ApprovalOutboxCleanerScheduler implements OutboxScheduler {

    private final ApprovalOutboxHelper approvalOutboxHelper;

    public ApprovalOutboxCleanerScheduler(ApprovalOutboxHelper approvalOutboxHelper) {
        this.approvalOutboxHelper = approvalOutboxHelper;
    }

    @Override
    @Scheduled(cron = "@midnight")
    public void processingOutboxMessage() {
        Optional<List<OrderApprovalOutboxMessage>> outboxMessageResponse = approvalOutboxHelper
                .getApprovalOutBoxMessageByOutboxStatusAndSagaStatus(OutboxStatus.COMPLETED,
                        SagaStatus.SUCCEEDED,
                        SagaStatus.FAILED,
                        SagaStatus.COMPENSATED);
        if (outboxMessageResponse.isPresent()) {
            List<OrderApprovalOutboxMessage> outboxMessages = outboxMessageResponse.get();
            log.info("Received {} OrderApprovalOutboxMessage for cleanup. The payloads: {}",
                    outboxMessages.size(),
                    outboxMessages.stream().map(OrderApprovalOutboxMessage::getPayload)
                            .collect(Collectors.joining("\n")));
            approvalOutboxHelper.deleteApprovalOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus.COMPLETED,
                    SagaStatus.SUCCEEDED,
                    SagaStatus.FAILED,
                    SagaStatus.COMPENSATED);
            log.info("{} OrderApprovalOutboxMessage deleted!", outboxMessages.size());
        }
    }
}
