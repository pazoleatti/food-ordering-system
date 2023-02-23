package org.example.restaurant.service.domain.event;

import org.example.domain.valueobject.RestaurantId;
import org.example.restaurant.service.domain.entity.OrderApproval;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderRejectedEvent extends OrderApprovalEvent {

    public OrderRejectedEvent(OrderApproval orderApproval,
                              RestaurantId restaurantId,
                              List<String> failureMessages,
                              ZonedDateTime createdAt) {
        super(orderApproval, restaurantId, failureMessages, createdAt);
    }

}
