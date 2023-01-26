package org.example.order.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.example.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import org.example.kafka.producer.KafkaMessageHelper;
import org.example.kafka.producer.service.KafkaProducer;
import org.example.order.service.domain.config.OrderServiceConfigData;
import org.example.order.service.domain.outbox.model.approval.OrderApprovalEventPayload;
import org.example.order.service.domain.outbox.model.approval.OrderApprovalOutboxMessage;
import org.example.order.service.domain.ports.output.message.publisher.restaurantapproval.RestaurantApprovalMessagePublisher;
import org.example.order.service.messaging.mapper.OrderMessagingDataMapper;
import org.example.outbox.OutboxStatus;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class OrderApprovalEventKafkaPublisher implements RestaurantApprovalMessagePublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaMessageHelper kafkaMessageHelper;

    public OrderApprovalEventKafkaPublisher(OrderMessagingDataMapper orderMessagingDataMapper,
                                            KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer,
                                            OrderServiceConfigData orderServiceConfigData,
                                            KafkaMessageHelper kafkaMessageHelper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
        this.kafkaProducer = kafkaProducer;
        this.orderServiceConfigData = orderServiceConfigData;
        this.kafkaMessageHelper = kafkaMessageHelper;
    }

    @Override
    public void publish(OrderApprovalOutboxMessage orderApprovalOutboxMessage,
                        BiConsumer<OrderApprovalOutboxMessage, OutboxStatus> outboxCallback) {
        OrderApprovalEventPayload
                orderApprovalEventPayload = kafkaMessageHelper.
                getOrderEventPayload(orderApprovalOutboxMessage.getPayload(), OrderApprovalEventPayload.class);
        String sagaId = orderApprovalOutboxMessage.getSagaId().toString();
        log.info("Received OrderApprovalOutboxMessage for order id: {} and saga id: {}",
                orderApprovalEventPayload.getOrderId(),
                sagaId);

        try {
            RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel = orderMessagingDataMapper.
                    orderApprovalEventToRestaurantApprovalRequestAvroModel(sagaId, orderApprovalEventPayload);

            kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                    sagaId,
                    restaurantApprovalRequestAvroModel,
                    kafkaMessageHelper.getKafkaCallback(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                            restaurantApprovalRequestAvroModel,
                            orderApprovalOutboxMessage,
                            outboxCallback,
                            orderApprovalEventPayload.getOrderId(),
                            "RestaurantApprovalRequestAvroModel"));
            log.info("OrderApprovalEventPayload sent to Kafka for order id: {} and saga id: {}",
                    orderApprovalEventPayload.getOrderId(),
                    sagaId);
        } catch (Exception e) {
            log.error("Error while sending OrderApprovalEventPayload to kafka with order id: {} and saga id: {}, error: {}",
                    orderApprovalEventPayload.getOrderId(),
                    sagaId,
                    e.getMessage());
        }
    }
}
