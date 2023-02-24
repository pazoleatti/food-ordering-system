package org.example.customer.service.domain.exception;

import org.example.domain.exception.DomainException;

public class CustomerDomainException extends DomainException {

    public CustomerDomainException(String message) {
        super(message);
    }
}
