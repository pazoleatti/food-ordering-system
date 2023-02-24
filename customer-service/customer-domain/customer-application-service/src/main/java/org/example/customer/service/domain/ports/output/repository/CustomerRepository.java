package org.example.customer.service.domain.ports.output.repository;

import org.example.customer.service.domain.entity.Customer;

public interface CustomerRepository {

    Customer createCustomer(Customer customer);
}
