package org.example.restaurant.service.domain;

import org.example.domain.event.publisher.DomainEventPublisher;
import org.example.restaurant.service.domain.entity.Restaurant;
import org.example.restaurant.service.domain.event.OrderApprovalEvent;
import org.example.restaurant.service.domain.event.OrderApprovedEvent;
import org.example.restaurant.service.domain.event.OrderRejectedEvent;

import java.util.List;

public interface RestaurantDomainService {

    OrderApprovalEvent validateOrder(Restaurant restaurant,
                                     List<String> failureMessages,
                                     DomainEventPublisher<OrderApprovedEvent> orderApprovedEventDomainEventPublisher,
                                     DomainEventPublisher<OrderRejectedEvent> orderRejectedEventDomainEventPublisher);
}
