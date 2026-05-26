package com.e_com.CartService.Cart.Application.DTO.Commands;

import java.util.UUID;

import lombok.Data;

@Data
public class CheckoutCartCommand {
    private UUID cartId;
    private UUID userId;
}
