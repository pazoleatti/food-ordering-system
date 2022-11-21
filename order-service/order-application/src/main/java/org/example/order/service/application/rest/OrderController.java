package org.example.order.service.application.rest;

import lombok.extern.slf4j.Slf4j;
import org.example.order.service.domain.dto.create.CreateOrderCommand;
import org.example.order.service.domain.dto.create.OrderResponseCommand;
import org.example.order.service.domain.dto.track.TrackOrderQuery;
import org.example.order.service.domain.dto.track.TrackOrderResponse;
import org.example.order.service.domain.ports.input.service.OrderApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.api.v1+json")
public class OrderController {
    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseCommand> createOrder(CreateOrderCommand createOrderCommand) {
        log.info("Create order for customer: {} at restaurant: {}", createOrderCommand.getCustomerId(),
                createOrderCommand.getRestaurantId());
        OrderResponseCommand orderResponseCommand = orderApplicationService.createOrder(createOrderCommand);
        log.info("Order created with tracking id: {}", orderResponseCommand.getOrderTrackingId());
        return ResponseEntity.ok(orderResponseCommand);
    }

    @GetMapping("/{trackingId}")
    public ResponseEntity<TrackOrderResponse> getOrderByTrackingId(@PathVariable UUID trackingId) {
        TrackOrderResponse trackOrderResponse =
                orderApplicationService.trackOrder(TrackOrderQuery.builder().orderTrackingId(trackingId).build());
        log.info("Returning order status with tracking id: {}", trackOrderResponse.getOrderTrackingId());
        return ResponseEntity.ok(trackOrderResponse);
    }
}
