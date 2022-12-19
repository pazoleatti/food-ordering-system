package org.example.service.dataaccess.order.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class OrderItemEntityId implements Serializable {
    private Long id;
    private OrderEntity order;
}
