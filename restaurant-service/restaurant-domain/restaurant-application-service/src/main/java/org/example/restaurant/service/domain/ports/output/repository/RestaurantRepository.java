package org.example.restaurant.service.domain.ports.output.repository;

import org.example.restaurant.service.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
    Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
