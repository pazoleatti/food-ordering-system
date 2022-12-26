package org.example.payment.service.domain.ports.output.repository;

import org.example.domain.valueobject.CustomerId;
import org.example.payment.service.domain.entity.CreditEntry;

import java.util.Optional;

public interface CreditEntryRepository {

    CreditEntry save(CreditEntry creditEntry);

    Optional<CreditEntry> findByCustomerId(CustomerId customerId);
}
