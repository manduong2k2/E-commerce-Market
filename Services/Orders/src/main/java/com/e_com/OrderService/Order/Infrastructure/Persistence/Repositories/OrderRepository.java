package com.e_com.OrderService.Order.Infrastructure.Persistence.Repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.e_com.OrderService.Order.Domain.Contracts.IOrderRepository;
import com.e_com.OrderService.Order.Domain.Models.Order;
import com.e_com.OrderService.Order.Infrastructure.Mapper.OrderMapper;
import com.e_com.OrderService.Order.Infrastructure.Persistence.Entities.OrderJpaEntity;

@Repository
public class OrderRepository implements IOrderRepository {

    private final OrderJpaRepository jpaRepository;
    private final OrderMapper mapper;

    public OrderRepository(OrderJpaRepository jpaRepository, OrderMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Order> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Order create(Order order) {
        OrderJpaEntity entity = mapper.toJpa(order);
        OrderJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Order update(Order order) {
        OrderJpaEntity entity = jpaRepository.findById(order.getId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + order.getId()));

        mapper.updateJpaFromDomain(order, entity);
        OrderJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void delete(UUID id) {
        jpaRepository.deleteById(id);
    }
}
