package com.e_com.CartService.Cart.Infrastructure.Persistence.Repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_com.CartService.Cart.Infrastructure.Persistence.Entities.CartEntity;

public interface JpaCartRepository extends JpaRepository<CartEntity, UUID> {
    Optional<CartEntity> findByUserId(UUID userId);
}
