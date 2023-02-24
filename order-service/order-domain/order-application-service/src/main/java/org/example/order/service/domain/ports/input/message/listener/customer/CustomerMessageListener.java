package org.example.order.service.domain.ports.input.message.listener.customer;

import org.example.order.service.domain.dto.message.CustomerModel;

public interface CustomerMessageListener {

    void customerCreated(CustomerModel customerModel);
}
