package com.e_com.CartService.Cart.Application.DTO.Responses;

import java.util.List;
import java.util.UUID;

import com.e_com.CartService.Cart.Domain.Model.Cart;

import lombok.Data;

@Data
public class CartResponse {
    private UUID id;
    private UUID userId;
    private String status;
    private List<CartItemResponse> items;
    private double total;
    private int totalItemCount;

    public static CartResponse from(Cart cart) {
        CartResponse response = new CartResponse();
        response.setId(cart.getId());
        response.setUserId(cart.getUserId());
        response.setStatus(cart.getStatus().getValue());
        response.setItems(
            cart.getItems().stream()
                .map(CartItemResponse::from)
                .toList()
        );
        response.setTotalItemCount(cart.getTotalItemCount());
        return response;
    }
}
