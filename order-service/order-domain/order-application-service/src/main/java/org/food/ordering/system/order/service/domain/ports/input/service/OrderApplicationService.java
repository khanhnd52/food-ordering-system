package org.food.ordering.system.order.service.domain.ports.input.service;

import org.food.ordering.system.order.service.domain.dto.create.CreateOderCommand;
import org.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import org.food.ordering.system.order.service.domain.dto.track.TrackOderQuery;
import org.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;

import javax.validation.Valid;

public interface OrderApplicationService {
    CreateOrderResponse createOrder(@Valid CreateOderCommand createOderCommand);
    TrackOrderResponse trackOrder(@Valid TrackOderQuery trackOderQuery);
}
