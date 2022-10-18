package org.example.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.order.service.domain.dto.create.CreateOrderCommand;
import org.example.order.service.domain.dto.create.OrderResponseCommand;
import org.example.order.service.domain.dto.track.TrackOrderQuery;
import org.example.order.service.domain.dto.track.TrackOrderResponse;
import org.example.order.service.domain.ports.input.service.OrderApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService {

    OrderCreateCommandHandler orderCreateCommandHandler;

    OrderTrackCommandHandler orderTrackCommandHandle;

    public OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler, OrderTrackCommandHandler orderTrackCommandHandle) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackCommandHandle = orderTrackCommandHandle;
    }

    @Override
    public OrderResponseCommand createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandle.trackOrder(trackOrderQuery);
    }
}
