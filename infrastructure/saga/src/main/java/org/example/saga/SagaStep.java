package org.example.saga;

import org.example.domain.event.DomainEvent;

public interface SagaStep<T> {
    void process(T data);
    void rollback(T data);
}
