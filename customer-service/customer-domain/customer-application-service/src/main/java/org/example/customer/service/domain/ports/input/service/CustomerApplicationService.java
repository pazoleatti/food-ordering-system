package org.example.customer.service.domain.ports.input.service;

import org.example.customer.service.domain.create.CreateCustomerCommand;
import org.example.customer.service.domain.create.CreateCustomerResponse;

import javax.validation.Valid;

public interface CustomerApplicationService {

    CreateCustomerResponse createCustomer(@Valid CreateCustomerCommand createCustomerCommand);

}
