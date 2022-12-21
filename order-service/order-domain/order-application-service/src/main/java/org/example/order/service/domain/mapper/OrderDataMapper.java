package org.example.order.service.domain.mapper;

import org.example.domain.valueobject.*;
import org.example.order.service.domain.dto.create.CreateOrderCommand;
import org.example.order.service.domain.dto.create.OrderAddress;
import org.example.order.service.domain.dto.create.CreateOrderResponse;
import org.example.order.service.domain.dto.track.TrackOrderResponse;
import org.example.order.service.domain.entity.Order;
import org.example.order.service.domain.entity.OrderItem;
import org.example.order.service.domain.entity.Product;
import org.example.order.service.domain.entity.Restaurant;
import org.example.order.service.domain.valueobject.StreetAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream().map(orderItem ->
                        new Product(new ProductId(orderItem.getProductId())))
                        .collect(Collectors.toList()))
                .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }

    public CreateOrderResponse createOrderToOrderResponse(Order order, String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();

    }
    private List<OrderItem> orderItemsToOrderItemEntities(
            List<org.example.order.service.domain.dto.create.OrderItem> items) {
        return items.stream().map(orderItem -> OrderItem.builder()
                .product(new Product(new ProductId(orderItem.getProductId())))
                .quantity(orderItem.getQuantity())
                .price(new Money(orderItem.getPrice()))
                .subTotal(new Money(orderItem.getSubTotal()))
                .build()).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress address) {
        return new StreetAddress(UUID.randomUUID(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity());
    }
}
