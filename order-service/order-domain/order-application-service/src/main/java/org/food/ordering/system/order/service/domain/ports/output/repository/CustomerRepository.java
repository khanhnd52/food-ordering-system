package org.food.ordering.system.order.service.domain.ports.output.repository;

import org.food.ordering.system.oder.service.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    Optional<Customer> findCustomer(UUID customerId);
}
