package org.example.order.service.domain.outbox.scheduler.approval;

import lombok.extern.slf4j.Slf4j;
import org.example.order.service.domain.exception.OrderDomainException;
import org.example.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import org.example.order.service.domain.ports.output.repository.ApprovalOutboxRepository;
import org.example.outbox.OutboxStatus;
import org.example.saga.SagaStatus;
import org.example.saga.order.SagaConstants;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Component
@Slf4j
public class ApprovalOutboxHelper {
    private final ApprovalOutboxRepository approvalOutboxRepository;

    public ApprovalOutboxHelper(ApprovalOutboxRepository approvalOutboxRepository) {
        this.approvalOutboxRepository = approvalOutboxRepository;
    }

    @Transactional(readOnly = true)
    Optional<List<OrderApprovalOutboxMessage>> getApprovalOutBoxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatuses) {
        return approvalOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus(ORDER_SAGA_NAME, outboxStatus, sagaStatuses);
    }

    @Transactional(readOnly = true)
    Optional<OrderApprovalOutboxMessage> getApprovalOutboxMessageBySagaIdAndSagaStatus(UUID sagaId, SagaStatus... sagaStatuses) {
        return approvalOutboxRepository.findByTypeAndSagaIdAndSagaStatus(ORDER_SAGA_NAME, sagaId, sagaStatuses);
    }

    @Transactional
    public void save(OrderApprovalOutboxMessage orderApprovalOutboxMessage) {
        OrderApprovalOutboxMessage response = approvalOutboxRepository.save(orderApprovalOutboxMessage);
        if (response == null) {
            log.error("Could not save OrderApprovalOutboxMessage with outbox id: {}", orderApprovalOutboxMessage.getId());
            throw new OrderDomainException("Could not save OrderPaymentOutboxMessage with outbox id: " +
                    orderApprovalOutboxMessage.getId());
        }
        log.info("OrderDomainOutboxMessage saved with outbox id: {}", orderApprovalOutboxMessage.getId());
    }

    @Transactional
    public void deleteApprovalOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        approvalOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus(ORDER_SAGA_NAME, outboxStatus, sagaStatuses);
    }
}
