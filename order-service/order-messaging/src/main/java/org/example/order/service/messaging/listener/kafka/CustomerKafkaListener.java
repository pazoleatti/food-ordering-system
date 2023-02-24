package org.example.order.service.messaging.listener.kafka;

import org.example.kafka.consumer.KafkaConsumer;
import org.example.kafka.order.avro.model.CustomerAvroModel;
import org.example.order.service.domain.ports.input.message.listener.customer.CustomerMessageListener;
import org.example.order.service.messaging.mapper.OrderMessagingDataMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CustomerKafkaListener implements KafkaConsumer<CustomerAvroModel> {

    private final CustomerMessageListener customerMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public CustomerKafkaListener(CustomerMessageListener customerMessageListener,
                                 OrderMessagingDataMapper orderMessagingDataMapper) {
        this.customerMessageListener = customerMessageListener;
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.customer-group-id}", topics = "${order-service.customer-topic-name}")
    public void receive(@Payload List<CustomerAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of customer create messages received with keys {}, partitions {} and offsets {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(customerAvroModel ->
                customerMessageListener.customerCreated(orderMessagingDataMapper
                        .customerAvroModeltoCustomerModel(customerAvroModel)));
    }
}
