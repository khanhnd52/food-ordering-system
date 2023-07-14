package org.food.ordering.system.order.service.domain;

import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.oder.service.domain.OrderDomainService;
import org.food.ordering.system.oder.service.domain.entity.Customer;
import org.food.ordering.system.oder.service.domain.entity.Order;
import org.food.ordering.system.oder.service.domain.entity.Restaurant;
import org.food.ordering.system.oder.service.domain.event.OrderCreatedEvent;
import org.food.ordering.system.oder.service.domain.exception.OrderDomainException;
import org.food.ordering.system.order.service.domain.dto.create.CreateOderCommand;
import org.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import org.food.ordering.system.order.service.domain.mapper.OrderDataMapper;
import org.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import org.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import org.food.ordering.system.order.service.domain.ports.output.repository.RestaurantRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateCommandHandler {
    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final CustomerRepository customerRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateCommandHandler(OrderDomainService orderDomainService, OrderRepository orderRepository, RestaurantRepository restaurantRepository, CustomerRepository customerRepository, OrderDataMapper orderDataMapper) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
        this.customerRepository = customerRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOderCommand createOderCommand) {
        checkCustomer(createOderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOderCommand);
        Order order = orderDataMapper.createOrderCommandToOrder(createOderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        Order orderResult = saveOrder(order);
        log.info("Order is created with id: {}", orderResult.getId().getValue());
        return orderDataMapper.orderToCreateOrderResponse(orderResult);
    }

    private Restaurant checkRestaurant(CreateOderCommand createOderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if (optionalRestaurant.isEmpty()) {
            log.warn("Could not find restaurant with restaurant id {}", createOderCommand.getRestaurantId());
            throw new OrderDomainException("Could not find restaurant with restaurant id: " +
                    createOderCommand.getRestaurantId());
        }
        return optionalRestaurant.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find customer with customer id: {}", customerId);
            throw new OrderDomainException("Could not find customer with customer id: " + customer);
        }
    }

    private Order saveOrder(Order order) {
        Order orderResult = orderRepository.save(order);
        if (orderResult == null) {
            log.error("Could not save order!");
            throw new OrderDomainException("Could not save order!");
        }
        log.info("Order is saved with id: {}", orderResult.getId().getValue());
        return orderResult;
    }
}
