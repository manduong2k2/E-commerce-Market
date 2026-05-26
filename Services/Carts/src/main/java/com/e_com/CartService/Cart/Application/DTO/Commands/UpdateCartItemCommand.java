package com.e_com.CartService.Cart.Application.DTO.Commands;

import java.util.UUID;

import lombok.Data;

@Data
public class UpdateCartItemCommand {
    private UUID userId;
    private UUID productVariantId;
    private int quantity;
}
