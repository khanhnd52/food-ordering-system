package org.food.ordering.system.order.service.domain.ports.output.message.publisher.payment;

import org.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import org.food.ordering.system.oder.service.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {

}
