package org.example.order.service.domain.outbox.scheduler.payment;

import lombok.extern.slf4j.Slf4j;
import org.example.order.service.domain.outbox.model.payment.OrderPaymentOutboxMessage;
import org.example.order.service.domain.ports.output.message.publisher.payment.PaymentRequestMessagePublisher;
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
public class PaymentOutboxScheduler implements OutboxScheduler {

    private final PaymentOutboxHelper paymentOutboxHelper;
    private final PaymentRequestMessagePublisher paymentRequestMessagePublisher;

    public PaymentOutboxScheduler(PaymentOutboxHelper paymentOutboxHelper, PaymentRequestMessagePublisher paymentRequestMessagePublisher) {
        this.paymentOutboxHelper = paymentOutboxHelper;
        this.paymentRequestMessagePublisher = paymentRequestMessagePublisher;
    }

    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${order-service.outbox-scheduler-fixed-rate}",
    initialDelayString = "${order-service.outbox-scheduler-initial-delay}")
    public void processingOutboxMessage() {
        Optional<List<OrderPaymentOutboxMessage>> outboxMessageResponse = paymentOutboxHelper.
                getPaymentOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus.STARTED,
                SagaStatus.STARTED,
                SagaStatus.COMPENSATING);
        if (outboxMessageResponse.isPresent() && !outboxMessageResponse.get().isEmpty()) {
            List<OrderPaymentOutboxMessage> outboxMessages = outboxMessageResponse.get();
            log.info("Received {} OrderPaymentOutboxMessage with ids: {}, sending to message bus!",
                    outboxMessages.size(),
                    outboxMessages.stream().map(outboxMessage ->
                            outboxMessage.getId().toString()).collect(Collectors.joining(",")));
            outboxMessages.forEach(outboxMessage ->
                    paymentRequestMessagePublisher.publish(outboxMessage, this::updateOutboxStatus));
            log.info("{} OrderPaymentOutboxMessage sent to message bus!", outboxMessages.size());
        }

    }

    private void updateOutboxStatus(OrderPaymentOutboxMessage orderPaymentOutboxMessage, OutboxStatus outboxStatus) {
        orderPaymentOutboxMessage.setOutboxStatus(outboxStatus);
        paymentOutboxHelper.save(orderPaymentOutboxMessage);
        log.info("OrderPaymentOutboxMessage is updated with outbox status: {}", outboxStatus.name());
    }

}
