package org.example.customer.service.domain.ports.output.message.publisher;

import org.example.customer.service.domain.event.CustomerCreatedEvent;

public interface CustomerMessagePublisher {

    void publish(CustomerCreatedEvent customerCreatedEvent);

}