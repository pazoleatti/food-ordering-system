package org.example.customer.service.domain;

import org.example.customer.service.domain.entity.Customer;
import org.example.customer.service.domain.event.CustomerCreatedEvent;

public interface CustomerDomainService {

    CustomerCreatedEvent validateAndInitiateCustomer(Customer customer);

}
