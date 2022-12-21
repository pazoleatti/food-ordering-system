package org.example.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.order.service.domain.dto.create.CreateOrderCommand;
import org.example.order.service.domain.dto.create.CreateOrderResponse;
import org.example.order.service.domain.event.OrderCreatedEvent;
import org.example.order.service.domain.mapper.OrderDataMapper;
import org.example.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCreateCommandHandler {

    private final OrderCreateHelper orderCreateHelper;
    private final OrderDataMapper orderDataMapper;

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    public OrderCreateCommandHandler(OrderCreateHelper orderCreateHelper,
                                     OrderDataMapper orderDataMapper,
                                     OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
        this.orderCreateHelper = orderCreateHelper;
        this.orderDataMapper = orderDataMapper;
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    }

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
        log.info("Order created with id: {}", orderCreatedEvent.getOrder().getId().getValue());
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        return orderDataMapper.createOrderToOrderResponse(orderCreatedEvent.getOrder(), "Order Created Successfully!");
    }
}
