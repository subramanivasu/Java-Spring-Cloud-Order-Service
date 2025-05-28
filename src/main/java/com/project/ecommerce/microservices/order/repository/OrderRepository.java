package com.project.ecommerce.microservices.order.repository;

import com.project.ecommerce.microservices.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
