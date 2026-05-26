package com.e_com.CartService.Cart.Domain.Contracts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.e_com.CartService.Cart.Domain.Models.Cart;

public interface ICartRepository {
    List<Cart> findAll();
    Cart create(Cart cart);
    Optional<Cart> findById(UUID id);
    Optional<Cart> findByUserId(UUID userId);
    Cart update(Cart cart);
    void delete(UUID id);
}