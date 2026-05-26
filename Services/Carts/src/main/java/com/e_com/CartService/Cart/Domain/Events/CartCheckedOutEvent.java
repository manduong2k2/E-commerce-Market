package com.e_com.CartService.Cart.Domain.Events;

import java.util.List;
import java.util.UUID;

import com.e_com.CartService.Shared.Domain.DomainEvent;

public class CartCheckedOutEvent extends DomainEvent {
    private final UUID cartId;
    private final UUID userId;
    private final List<CartItemSnapshot> items;

    public CartCheckedOutEvent(UUID cartId, UUID userId, List<CartItemSnapshot> items) {
        super();
        this.cartId = cartId;
        this.userId = userId;
        this.items = items;
    }

    public UUID getCartId() {
        return cartId;
    }

    public UUID getUserId() {
        return userId;
    }

    public List<CartItemSnapshot> getItems() {
        return items;
    }

    public static class CartItemSnapshot {
        private final UUID itemId;
        private final UUID productVariantId;
        private final int quantity;

        public CartItemSnapshot(UUID itemId, UUID productVariantId, int quantity) {
            this.itemId = itemId;
            this.productVariantId = productVariantId;
            this.quantity = quantity;
        }

        public UUID getItemId() {
            return itemId;
        }

        public UUID getProductVariantId() {
            return productVariantId;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
