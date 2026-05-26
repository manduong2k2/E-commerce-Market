package com.e_com.CartService.Cart.Application.Services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.e_com.CartService.Cart.Application.DTO.Commands.AddItemToCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.CheckoutCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.RemoveItemFromCartCommand;
import com.e_com.CartService.Cart.Application.DTO.Commands.UpdateCartItemCommand;
import com.e_com.CartService.Cart.Domain.Contracts.ICartRepository;
import com.e_com.CartService.Cart.Domain.Contracts.ICartService;
import com.e_com.CartService.Cart.Domain.Events.CartCheckedOutEvent;
import com.e_com.CartService.Cart.Domain.Events.CartCreatedEvent;
import com.e_com.CartService.Cart.Domain.Events.CartItemAddedEvent;
import com.e_com.CartService.Cart.Domain.Models.Cart;
import com.e_com.CartService.Cart.Domain.Models.CartItem;
import com.e_com.CartService.Cart.Infrastructure.Config.CartQueueConfig;
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
    public Cart addItem(AddItemToCartCommand command) {
        Cart cart = repository.findByUserId(command.getUserId())
                .orElseGet(() -> {
                    try {
                        Cart newCart = new Cart(null, command.getUserId());
                        Cart saved = repository.create(newCart);
                        saved.addDomainEvent(new CartCreatedEvent(saved.getId(), saved.getUserId()));
                        publishDomainEvents(saved, CartQueueConfig.CART_CREATED);
                        return saved;
                    } catch (DataIntegrityViolationException e) {
                        return repository.findByUserId(command.getUserId()).orElseThrow();
                    }
                });

        CartItem item = new CartItem(
                null,
                command.getProductVariantId(),
                command.getQuantity());
        cart.addItem(item);
        Cart updated = repository.update(cart);

        updated.addDomainEvent(new CartItemAddedEvent(
                updated.getId(),
                command.getProductVariantId(),
                command.getQuantity()));
        publishDomainEvents(updated, CartQueueConfig.CART_ITEM_ADDED);

        return updated;
    }

    @Override
    public Cart removeItem(RemoveItemFromCartCommand command) {
        Cart cart = getByUserId(command.getUserId());
        cart.removeItem(command.getProductVariantId());
        return repository.update(cart);
    }

    @Override
    public Cart updateItem(UpdateCartItemCommand command) {
        Cart cart = getByUserId(command.getUserId());
        cart.updateItemQuantity(command.getProductVariantId(), command.getQuantity());
        return repository.update(cart);
    }

    @Override
    public Cart checkout(CheckoutCartCommand command) {
        Cart cart = getByUserId(command.getUserId());
        cart.checkout();
        Cart updated = repository.update(cart);

        List<CartCheckedOutEvent.CartItemSnapshot> itemSnapshots = updated.getItems().stream()
                .map(item -> new CartCheckedOutEvent.CartItemSnapshot(
                        item.getId(),
                        item.getProductVariantId(),
                        item.getQuantity()))
                .toList();

        updated.addDomainEvent(new CartCheckedOutEvent(
                updated.getId(),
                updated.getUserId(),
                itemSnapshots));
        publishDomainEvents(updated, CartQueueConfig.CART_CHECKED_OUT);

        return updated;
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

    private void publishDomainEvents(AggregateRoot<?> aggregate, String queue) {
        aggregate.getDomainEvents()
                .forEach(event -> eventPublisher.publish(event, new EventOptions(queue, false)));
        aggregate.clearDomainEvents();
    }
}
