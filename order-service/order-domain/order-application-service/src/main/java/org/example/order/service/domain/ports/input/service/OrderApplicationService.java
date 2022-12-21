package org.example.order.service.domain.ports.input.service;

import org.example.order.service.domain.dto.create.CreateOrderCommand;
import org.example.order.service.domain.dto.create.CreateOrderResponse;
import org.example.order.service.domain.dto.track.TrackOrderQuery;
import org.example.order.service.domain.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OrderApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);
    TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
