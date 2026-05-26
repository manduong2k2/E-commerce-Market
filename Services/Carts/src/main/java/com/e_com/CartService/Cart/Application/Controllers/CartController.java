package com.e_com.CartService.Cart.Application.Controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_com.CartService.Cart.Application.DTO.Commands.AddItemToCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.CheckoutCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.RemoveItemFromCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.UpdateCartItemCommand;
import com.e_com.CartService.Cart.Application.DTO.Requests.AddItemRequest;
import com.e_com.CartService.Cart.Application.DTO.Requests.UpdateItemRequest;
import com.e_com.CartService.Cart.Application.DTO.Responses.CartResponse;
import com.e_com.CartService.Cart.Domain.Contracts.ICartService;
import com.e_com.CartService.Cart.Domain.Models.Cart;
import com.e_com.CartService.Shared.Application.Annotation.Auth.Authenticated;
import com.e_com.CartService.Shared.Application.Auth.ContextHolder;
import com.e_com.CartService.Shared.Infrastructure.Constants.Http;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
@Authenticated
public class CartController {

    @Autowired
    private ICartService cartService;

    // GET /api/cart
    @Authenticated
    @GetMapping
    public ResponseEntity<Map<String, Object>> getMyCart() {

        UUID userId = ContextHolder.getUser().getId();
        Cart cart = cartService.getByUserId(userId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);

        if (cart == null) {
            response.put("cart", null);
            response.put("message", "Cart is empty");
        } else {
            response.put("cart", CartResponse.from(cart));
        }
        return ResponseEntity.ok(response);
    }


    // POST /api/cart/items
    @PostMapping("/items")
    @Authenticated
    public ResponseEntity<CartResponse> addItem(@Valid @RequestBody AddItemRequest request) {
        UUID userId = ContextHolder.getUser().getId();

        AddItemToCartCommand command = new AddItemToCartCommand();
        command.setUserId(userId);
        command.setProductVariantId(request.getProductVariantId());
        command.setQuantity(request.getQuantity());

        CartResponse response = CartResponse.from(cartService.addItem(command));

        return ResponseEntity.status(Http.OK).body(response);
    }

    // PATCH /api/cart/items/{productVariantId}
    @Authenticated
    @PatchMapping("/items/{productVariantId}")
    public ResponseEntity<CartResponse> updateItem(
            @PathVariable UUID productVariantId,
            @Valid @RequestBody UpdateItemRequest request) {

        UUID userId = ContextHolder.getUser().getId();

        UpdateCartItemCommand command = new UpdateCartItemCommand();
        command.setUserId(userId);
        command.setProductVariantId(productVariantId);
        command.setQuantity(request.getQuantity());

        CartResponse response = CartResponse.from(cartService.updateItem(command));

        return ResponseEntity.status(Http.OK).body(response);
    }

    // DELETE /api/cart/items/{productVariantId}
    @Authenticated
    @DeleteMapping("/items/{productVariantId}")
    public ResponseEntity<CartResponse> removeItem(@PathVariable UUID productVariantId) {
        UUID userId = ContextHolder.getUser().getId();

        RemoveItemFromCartCommand command = new RemoveItemFromCartCommand();
        command.setUserId(userId);
        command.setProductVariantId(productVariantId);

        CartResponse response = CartResponse.from(cartService.removeItem(command));

        return ResponseEntity.status(Http.OK).body(response);
    }

    // POST /api/cart/checkout
    @Authenticated
    @PostMapping("/checkout")
    public ResponseEntity<CartResponse> checkout() {
        UUID userId = ContextHolder.getUser().getId();

        CheckoutCartCommand command = new CheckoutCartCommand();
        command.setUserId(userId);

        CartResponse response = CartResponse.from(cartService.checkout(command));

        return ResponseEntity.status(Http.OK).body(response);
    }
}
