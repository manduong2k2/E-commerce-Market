package com.e_com.CartService.Cart.Domain.Events;

import java.util.UUID;

import com.e_com.CartService.Shared.Domain.DomainEvent;

public class CartItemAddedEvent extends DomainEvent {
    private final UUID cartId;
    private final UUID productVariantId;
    private final int quantity;

    public CartItemAddedEvent(UUID cartId, UUID productVariantId, int quantity) {
        super();
        this.cartId = cartId;
        this.productVariantId = productVariantId;
        this.quantity = quantity;
    }

    public UUID getCartId() {
        return cartId;
    }

    public UUID getProductVariantId() {
        return productVariantId;
    }

    public int getQuantity() {
        return quantity;
    }
}
