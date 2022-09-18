package org.example.domain.valueobject;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@EqualsAndHashCode
public class Money {

    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isGreaterThanZero() {
        return amount != null && BigDecimal.ZERO.compareTo(amount) > 0;
    }

    public boolean isGreaterThan(Money money) {
        return this.amount != null && amount.compareTo(money.getAmount()) > 0;
    }

    public Money add(Money money) {
        return new Money(setScale(amount.add(money.getAmount())));
    }

    public Money subtract(Money money) {
        return new Money(setScale(amount.subtract(money.getAmount())));
    }
    
    public Money multiply(Money money) {
        return new Money(setScale(amount.multiply(money.getAmount())));
    }

    private BigDecimal setScale(BigDecimal input) {
        return input.setScale(2, RoundingMode.HALF_EVEN);
    }
}
