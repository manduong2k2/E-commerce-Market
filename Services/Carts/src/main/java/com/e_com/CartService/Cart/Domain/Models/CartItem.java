package com.e_com.CartService.Cart.Domain.Models;

import java.util.UUID;
import com.e_com.CartService.Shared.Domain.Entity;

public class CartItem extends Entity<UUID> {
    private UUID productVariantId;
    private Quantity quantity;

    public CartItem() {
        super(null);
    }

    public CartItem(UUID id, UUID productVariantId, int quantity) {
        super(id);
        this.productVariantId = productVariantId;
        this.quantity = new Quantity(quantity);
    }

    // Business methods

    public void increaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        this.quantity.setValue(this.quantity.getValue() + amount);
    }

    public void decreaseQuantity(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        int newQty = this.quantity.getValue() - amount;
        if (newQty < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity.setValue(newQty);
    }

    public void updateQuantity(int quantity) {
        this.quantity.setValue(quantity);
    }

    // Base methods

    public UUID getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(UUID productVariantId) {
        this.productVariantId = productVariantId;
    }

    public int getQuantity() {
        return quantity.getValue();
    }

    public void setQuantity(int quantity) {
        this.quantity.setValue(quantity);
    }
}
