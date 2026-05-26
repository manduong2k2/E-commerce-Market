package com.e_com.CartService.Cart.Domain.Events;

import java.util.UUID;

import com.e_com.CartService.Shared.Domain.DomainEvent;

public class CartCheckedOutEvent extends DomainEvent {
    private final UUID cartId;
    private final UUID userId;

    public CartCheckedOutEvent(UUID cartId, UUID userId) {
        super();
        this.cartId = cartId;
        this.userId = userId;
    }

    public UUID getCartId() {
        return cartId;
    }

    public UUID getUserId() {
        return userId;
    }
}
