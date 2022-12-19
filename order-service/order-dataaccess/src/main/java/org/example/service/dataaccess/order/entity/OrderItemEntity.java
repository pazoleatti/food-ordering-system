package org.example.service.dataaccess.order.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "order_items")
@Entity
@IdClass(OrderItemEntityId.class)
@EqualsAndHashCode(of = {"id","order"})
public class OrderItemEntity {
    @Id
    private Long id;
    @Id
    @JoinColumn(name = "ORDER_ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private OrderEntity order;

    private UUID productId;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal subtotal;
}
