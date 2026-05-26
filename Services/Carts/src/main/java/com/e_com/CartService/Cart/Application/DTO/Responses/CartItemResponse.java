package com.e_com.CartService.Cart.Application.DTO.Responses;

import java.util.UUID;

import com.e_com.CartService.Cart.Domain.Models.CartItem;

import lombok.Data;

@Data
public class CartItemResponse {
    private UUID id;
    private UUID productVariantId;
    private int quantity;
    private double subTotal;

    public static CartItemResponse from(CartItem item) {
        CartItemResponse response = new CartItemResponse();
        response.setId(item.getId());
        response.setProductVariantId(item.getProductVariantId());
        response.setQuantity(item.getQuantity());
        return response;
    }
}
