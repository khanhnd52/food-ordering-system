package org.food.ordering.system.order.service.domain.mapper;

import org.food.ordering.system.domain.valueobject.CustomerId;
import org.food.ordering.system.domain.valueobject.Money;
import org.food.ordering.system.domain.valueobject.ProductId;
import org.food.ordering.system.domain.valueobject.RestaurantId;
import org.food.ordering.system.oder.service.domain.entity.Order;
import org.food.ordering.system.oder.service.domain.entity.OrderItem;
import org.food.ordering.system.oder.service.domain.entity.Product;
import org.food.ordering.system.oder.service.domain.entity.Restaurant;
import org.food.ordering.system.oder.service.domain.valueobject.StreetAddress;
import org.food.ordering.system.order.service.domain.dto.create.CreateOderCommand;
import org.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import org.food.ordering.system.order.service.domain.dto.create.OrderAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOderCommand createOderCommand) {
        return Restaurant.Builder.builder()
                .restaurantId(new RestaurantId(createOderCommand.getRestaurantId()))
                .products(createOderCommand.getItems().stream().map(orderItem ->
                        new Product(new ProductId(orderItem.getProductId())))
                        .collect(Collectors.toList()))
                .build();

    }

    public Order createOrderCommandToOrder(CreateOderCommand createOderCommand) {
        return Order.Builder.builder()
                .customerId(new CustomerId(createOderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOderCommand.getAddress()))
                .price(new Money(createOderCommand.getPrice()))
                .items(orderItemsToOrderItemEntities(createOderCommand.getItems()))
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(
            List<org.food.ordering.system.order.service.domain.dto.create.OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem ->
                        OrderItem.Builder.builder()
                                .product(new Product(new ProductId(orderItem.getProductId())))
                                .price(new Money(orderItem.getPrice()))
                                .quantity(orderItem.getQuantity())
                                .subTotal(new Money(orderItem.getSubTotal()))
                                .build()).collect(Collectors.toList());

    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
        );
    }
}
