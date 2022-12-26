package org.example.payment.service.domain;

import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.payment.service.domain.entity.CreditEntry;
import org.example.payment.service.domain.entity.CreditHistory;
import org.example.payment.service.domain.entity.Payment;
import org.example.payment.service.domain.event.PaymentCancelledEvent;
import org.example.payment.service.domain.event.PaymentCompletedEvent;
import org.example.payment.service.domain.event.PaymentEvent;
import org.example.payment.service.domain.event.PaymentFailedEvent;

import java.util.List;

public interface PaymentDomainService {

    PaymentEvent validateAndInitiatePayment(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages,
                                            DomainEventPublisher<PaymentCompletedEvent>
                                                    paymentCompletedEventDomainEventPublisher, DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher);

    PaymentEvent validateAndCancelPayment(Payment payment,
                                          CreditEntry creditEntry,
                                          List<CreditHistory> creditHistories,
                                          List<String> failureMessages, DomainEventPublisher<PaymentCancelledEvent> paymentCancelledEventDomainEventPublisher, DomainEventPublisher<PaymentFailedEvent> paymentFailedEventDomainEventPublisher);
}
