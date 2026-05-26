package com.e_com.OrderService.Order.Application.Consumers;

import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.e_com.OrderService.Order.Application.DTO.Commands.CreateOrderCommand;
import com.e_com.OrderService.Order.Application.DTO.Messages.CartCheckedOutMessage;
import com.e_com.OrderService.Order.Application.DTO.Messages.CartCheckedOutMessage.CartItemMessage;
import com.e_com.OrderService.Order.Application.Services.OrderService;
import com.e_com.OrderService.Order.Domain.Model.OrderItem;
import com.e_com.OrderService.Order.Domain.Model.ProductSnapShot;

@Component
public class CartCheckedOutConsumer {

    private final OrderService orderService;

    public CartCheckedOutConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "${rabbitmq.queues.cart-checked-out}")
    public void handle(CartCheckedOutMessage message) {
        List<OrderItem> items = message.getItems().stream()
                .map(this::toOrderItem)
                .toList();

        CreateOrderCommand command = new CreateOrderCommand();
        command.setCartId(message.getCartId());
        command.setUserId(message.getUserId());
        command.setItems(items);

        orderService.create(command);
    }

    private OrderItem toOrderItem(CartItemMessage itemMessage) {
        OrderItem item = new OrderItem(
                itemMessage.getItemId(),
                itemMessage.getProductVariantId(),
                itemMessage.getQuantity()
        );

        ProductSnapShot snapShot = new ProductSnapShot(
                UUID.randomUUID(),
                itemMessage.getProductId(),
                itemMessage.getProductName(),
                itemMessage.getProductCode(),
                itemMessage.getBrand(),
                itemMessage.getCategories(),
                itemMessage.getVariantId(),
                itemMessage.getPrice(),
                itemMessage.getVariantCode(),
                itemMessage.getVariantName()
        );

        item.setSnapShot(snapShot);
        return item;
    }
}
