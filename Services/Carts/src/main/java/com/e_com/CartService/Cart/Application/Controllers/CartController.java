package com.e_com.CartService.Cart.Application.Controllers;

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
import com.e_com.CartService.Cart.Application.DTO.Commands.CreateCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.RemoveItemFromCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.UpdateCartItemCommand;
import com.e_com.CartService.Cart.Application.DTO.Requests.AddItemRequest;
import com.e_com.CartService.Cart.Application.DTO.Requests.UpdateItemRequest;
import com.e_com.CartService.Cart.Application.DTO.Responses.CartResponse;
import com.e_com.CartService.Cart.Domain.Contracts.ICartService;
import com.e_com.CartService.Shared.Application.Annotation.Auth.Authenticated;
import com.e_com.CartService.Shared.Application.Auth.ContextHolder;
import com.e_com.CartService.Shared.Infrastructure.Constants.Http;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/carts")
@Authenticated
public class CartController {

    @Autowired
    private ICartService cartService;

    // GET /api/carts/me
    @GetMapping("/me")
    public ResponseEntity<CartResponse> getMyCart() {
        UUID userId = ContextHolder.getUser().getId();

        CartResponse response = CartResponse.from(cartService.getByUserId(userId));

        return ResponseEntity.status(Http.OK).body(response);
    }

    // GET /api/carts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getById(@PathVariable UUID id) {
        CartResponse response = CartResponse.from(cartService.getById(id));

        return ResponseEntity.status(Http.OK).body(response);
    }

    // POST /api/carts
    @PostMapping
    public ResponseEntity<CartResponse> create() {
        UUID userId = ContextHolder.getUser().getId();

        CreateCartCommand command = new CreateCartCommand();
        command.setUserId(userId);

        CartResponse response = CartResponse.from(cartService.create(command));

        return ResponseEntity.status(Http.CREATED).body(response);
    }

    // POST /api/carts/{id}/items
    @PostMapping("/{id}/items")
    public ResponseEntity<CartResponse> addItem(
            @PathVariable UUID id,
            @Valid @RequestBody AddItemRequest request) {

        UUID userId = ContextHolder.getUser().getId();

        AddItemToCartCommand command = new AddItemToCartCommand();
        command.setCartId(id);
        command.setUserId(userId);
        command.setProductVariantId(request.getProductVariantId());
        command.setQuantity(request.getQuantity());

        CartResponse response = CartResponse.from(cartService.addItem(command));

        return ResponseEntity.status(Http.OK).body(response);
    }

    // PATCH /api/carts/{id}/items/{productVariantId}
    @PatchMapping("/{id}/items/{productVariantId}")
    public ResponseEntity<CartResponse> updateItem(
            @PathVariable UUID id,
            @PathVariable UUID productVariantId,
            @Valid @RequestBody UpdateItemRequest request) {

        UUID userId = ContextHolder.getUser().getId();

        UpdateCartItemCommand command = new UpdateCartItemCommand();
        command.setCartId(id);
        command.setUserId(userId);
        command.setProductVariantId(productVariantId);
        command.setQuantity(request.getQuantity());

        CartResponse response = CartResponse.from(cartService.updateItem(command));

        return ResponseEntity.status(Http.OK).body(response);
    }

    // DELETE /api/carts/{id}/items/{productVariantId}
    @DeleteMapping("/{id}/items/{productVariantId}")
    public ResponseEntity<CartResponse> removeItem(
            @PathVariable UUID id,
            @PathVariable UUID productVariantId) {

        UUID userId = ContextHolder.getUser().getId();

        RemoveItemFromCartCommand command = new RemoveItemFromCartCommand();
        command.setCartId(id);
        command.setUserId(userId);
        command.setProductVariantId(productVariantId);

        CartResponse response = CartResponse.from(cartService.removeItem(command));

        return ResponseEntity.status(Http.OK).body(response);
    }

    // POST /api/carts/{id}/checkout
    @PostMapping("/{id}/checkout")
    public ResponseEntity<CartResponse> checkout(@PathVariable UUID id) {
        UUID userId = ContextHolder.getUser().getId();

        CheckoutCartCommand command = new CheckoutCartCommand();
        command.setCartId(id);
        command.setUserId(userId);

        CartResponse response = CartResponse.from(cartService.checkout(command));

        return ResponseEntity.status(Http.OK).body(response);
    }
}
