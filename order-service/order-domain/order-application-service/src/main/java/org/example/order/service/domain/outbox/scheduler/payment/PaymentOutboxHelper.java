package org.example.order.service.domain.outbox.scheduler.payment;

import lombok.extern.slf4j.Slf4j;
import org.example.order.service.domain.exception.OrderDomainException;
import org.example.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import org.example.order.service.domain.ports.output.repository.PaymentOutboxRepository;
import org.example.outbox.OutboxStatus;
import org.example.saga.SagaStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.example.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Component
@Slf4j
public class PaymentOutboxHelper {
    private final PaymentOutboxRepository paymentOutboxRepository;

    public PaymentOutboxHelper(PaymentOutboxRepository paymentOutboxRepository) {
        this.paymentOutboxRepository = paymentOutboxRepository;
    }

    @Transactional(readOnly = true)
    public Optional<List<OrderPaymentOutboxMessage>> getPaymentOutboxMessageByOutboxStatusAndSagaStatus(
            OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        return paymentOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus(ORDER_SAGA_NAME, outboxStatus, sagaStatuses);
    }

    @Transactional(readOnly = true)
    public Optional<OrderPaymentOutboxMessage> getPaymentOutboxMessageBySagaIdAndSagaStatus(UUID sagaId,
                                                                                            SagaStatus... sagaStatuses) {
        return paymentOutboxRepository.findByTypeAndSagaIdAndSagaStatus(ORDER_SAGA_NAME, sagaId, sagaStatuses);
    }

    @Transactional
    public void save(OrderPaymentOutboxMessage orderPaymentOutboxMessage) {
        OrderPaymentOutboxMessage response = paymentOutboxRepository.save(orderPaymentOutboxMessage);
        if (response == null) {
            log.error("Could not save OrderPaymentOutboxMessage with outbox id: {}", orderPaymentOutboxMessage.getId());
            throw new OrderDomainException("Could not save OrderPaymentOutboxMessage with outbox id: " +
                    orderPaymentOutboxMessage.getId());
        }
        log.info("OrderDomainOutboxMessage saved with outbox id: {}", orderPaymentOutboxMessage.getId());
    }

    @Transactional
    public void deletePaymentOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus outboxStatus,
                                                                      SagaStatus... sagaStatuses) {
        paymentOutboxRepository.deleteByTypeAndOutboxStatusAndSagaStatus(ORDER_SAGA_NAME, outboxStatus, sagaStatuses);
    }
}
