package com.e_com.OrderService.Order.Infrastructure.Persistence.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_com.OrderService.Order.Infrastructure.Persistence.Entities.OrderJpaEntity;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {
}
