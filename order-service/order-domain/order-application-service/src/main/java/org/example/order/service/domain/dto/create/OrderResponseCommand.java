package org.example.order.service.domain.dto.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.domain.valueobject.OrderStatus;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
public class OrderResponseCommand {
    @NotNull
    private final UUID orderTrackingId;
    @NotNull
    private final OrderStatus orderStatus;
    @NotNull
    private final String message;
}
