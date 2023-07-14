package org.food.ordering.system.oder.service.domain.event;

import org.food.ordering.system.domain.event.DomainEvent;
import org.food.ordering.system.oder.service.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent {


    public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
