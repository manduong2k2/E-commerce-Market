package com.e_com.CartService.Cart.Application.Services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.e_com.CartService.Cart.Application.DTO.Commands.AddItemToCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.CheckoutCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.CreateCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.RemoveItemFromCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.UpdateCartItemCommand;
import com.e_com.CartService.Cart.Domain.Contracts.ICartRepository;
import com.e_com.CartService.Cart.Domain.Contracts.ICartService;
import com.e_com.CartService.Cart.Domain.Events.CartCheckedOutEvent;
import com.e_com.CartService.Cart.Domain.Events.CartCreatedEvent;
import com.e_com.CartService.Cart.Domain.Events.CartItemAddedEvent;
import com.e_com.CartService.Cart.Domain.Model.Cart;
import com.e_com.CartService.Cart.Domain.Model.CartItem;
import com.e_com.CartService.Shared.Domain.AggregateRoot;
import com.e_com.CartService.Shared.Domain.Contract.IEventPublisher;
import com.e_com.CartService.Shared.Infrastructure.Event.EventOptions;

@Service
public class CartService implements ICartService {

    @Autowired
    private ICartRepository repository;

    @Autowired
    private IEventPublisher eventPublisher;

    @Override
    public Cart create(CreateCartCommand command) {
        Cart cart = new Cart(UUID.randomUUID(), command.getUserId());
        repository.create(cart);

        cart.addDomainEvent(new CartCreatedEvent(cart.getId(), cart.getUserId()));
        publishDomainEvents(cart, "cart.created");

        return cart;
    }

    @Override
    public Cart addItem(AddItemToCartCommand command) {
        Cart cart = getById(command.getCartId());

        CartItem item = new CartItem(
            UUID.randomUUID(),
            command.getProductVariantId(),
            command.getQuantity()
        );
        cart.addItem(item);
        repository.update(cart);

        cart.addDomainEvent(new CartItemAddedEvent(
            cart.getId(),
            command.getProductVariantId(),
            command.getQuantity()
        ));
        publishDomainEvents(cart, "cart.item.added");

        return cart;
    }

    @Override
    public Cart removeItem(RemoveItemFromCartCommand command) {
        Cart cart = getById(command.getCartId());
        cart.removeItem(command.getProductVariantId());
        repository.update(cart);
        return cart;
    }

    @Override
    public Cart updateItem(UpdateCartItemCommand command) {
        Cart cart = getById(command.getCartId());
        cart.updateItemQuantity(command.getProductVariantId(), command.getQuantity());
        repository.update(cart);
        return cart;
    }

    @Override
    public Cart checkout(CheckoutCartCommand command) {
        Cart cart = getById(command.getCartId());
        cart.checkout();
        repository.update(cart);

        cart.addDomainEvent(new CartCheckedOutEvent(
            cart.getId(),
            cart.getUserId()
        ));
        publishDomainEvents(cart, "cart.checked_out");

        return cart;
    }

    @Override
    public Cart getByUserId(UUID userId) {
        return repository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + userId));
    }

    @Override
    public Cart getById(UUID id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cart not found: " + id));
    }

    @Async
    private void publishDomainEvents(AggregateRoot<?> aggregate, String queue) {
        aggregate.getDomainEvents()
            .forEach(event -> eventPublisher.publish(event, new EventOptions(queue, false)));
        aggregate.clearDomainEvents();
    }
}
