package org.example.order.service.dataaccess.order.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "order_address")
@Entity
@EqualsAndHashCode(of = "id")
public class OrderAddressEntity {
    @Id
    private UUID id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ORDER_ID")
    private OrderEntity order;

    private String street;
    private String postalCode;
    private String city;
}
