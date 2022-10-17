package org.example.order.service.domain.ports.output.repository;

import org.example.domain.valueobject.RestaurantId;
import org.example.order.service.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(RestaurantId restaurantId);
}
