package com.microservices.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
