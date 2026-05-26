package com.e_com.OrderService.Order.Application.DTO.Messages;

import java.util.List;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Message payload published by the Cart service when a cart is checked out.
 * Consumed by CartCheckedOutConsumer to create a new Order.
 * Note: product snapshot fields (name, price, brand, etc.) are optional —
 * they will be null if the Cart service does not enrich the event.
 */
@Data
@NoArgsConstructor
public class CartCheckedOutMessage {
    private UUID cartId;
    private UUID userId;
    private List<CartItemMessage> items;

    @Data
    @NoArgsConstructor
    public static class CartItemMessage {
        private UUID itemId;
        private String productVariantId;
        private int quantity;
        // Product snapshot fields — populated if available
        private double price;
        private String productName;
        private String productCode;
        private String brand;
        private List<String> categories;
        private UUID productId;
        private UUID variantId;
        private String variantName;
        private String variantCode;
    }
}
