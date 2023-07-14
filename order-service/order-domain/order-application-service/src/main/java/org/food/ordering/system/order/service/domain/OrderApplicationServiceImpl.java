package org.food.ordering.system.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.order.service.domain.dto.create.CreateOderCommand;
import org.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import org.food.ordering.system.order.service.domain.dto.track.TrackOderQuery;
import org.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import org.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class OrderApplicationServiceImpl implements OrderApplicationService {
    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;

    public OrderApplicationServiceImpl(OrderCreateCommandHandler orderCreateCommandHandler, OrderTrackCommandHandler orderTrackCommandHandler) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackCommandHandler = orderTrackCommandHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOderCommand createOderCommand) {
        return orderCreateCommandHandler.createOrder(createOderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOderQuery trackOderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOderQuery);
    }
}
