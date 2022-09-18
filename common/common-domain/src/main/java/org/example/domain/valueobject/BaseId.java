package org.example.domain.valueobject;

import lombok.*;

@Getter
@EqualsAndHashCode
public abstract class BaseId<T> {

    private final T value;

    public BaseId(T value) {
        this.value = value;
    }
}
