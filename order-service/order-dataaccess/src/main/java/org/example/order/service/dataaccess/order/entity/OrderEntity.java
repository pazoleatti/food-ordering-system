package org.example.order.service.dataaccess.order.entity;

import lombok.*;
import org.example.domain.valueobject.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "orders")
@Entity
@EqualsAndHashCode(of = "id")
public class OrderEntity {
    @Id
    private UUID id;
    private UUID customerId;
    private UUID restaurantId;
    private UUID trackingId;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String failureMessages;
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderAddressEntity orderAddressEntity;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> items;
}
