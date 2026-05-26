package com.e_com.CartService.Cart.Domain.Contracts;

import java.util.UUID;

import com.e_com.CartService.Cart.Application.DTO.Commands.AddItemToCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.CheckoutCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.CreateCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.RemoveItemFromCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.UpdateCartItemCommand;
import com.e_com.CartService.Cart.Domain.Model.Cart;

public interface ICartService {
    Cart create(CreateCartCommand command);
    Cart addItem(AddItemToCartCommand command);
    Cart removeItem(RemoveItemFromCartCommand command);
    Cart updateItem(UpdateCartItemCommand command);
    Cart checkout(CheckoutCartCommand command);
    Cart getByUserId(UUID userId);
    Cart getById(UUID id);
}
