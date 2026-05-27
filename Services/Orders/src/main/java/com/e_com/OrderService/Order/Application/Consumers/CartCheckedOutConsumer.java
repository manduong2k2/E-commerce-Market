package com.e_com.OrderService.Order.Application.Consumers;

import java.util.List;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.e_com.OrderService.Order.Application.DTO.Commands.CreateOrderCommand;
import com.e_com.OrderService.Order.Application.DTO.Messages.CartCheckedOutMessage;
import com.e_com.OrderService.Order.Application.DTO.Messages.CartCheckedOutMessage.CartItemMessage;
import com.e_com.OrderService.Order.Application.Services.OrderService;
import com.e_com.OrderService.Order.Domain.Models.OrderItem;

@Component
public class CartCheckedOutConsumer {

    private final OrderService orderService;

    public CartCheckedOutConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "${rabbitmq.queues.cart-checked-out}")
    public void handle(CartCheckedOutMessage message) {
        try {
            System.out.println("consumed event cart checked out");

            List<OrderItem> items = message.getItems().stream()
                    .map(this::toOrderItem)
                    .toList();

            CreateOrderCommand command = new CreateOrderCommand();
            command.setCartId(message.getCartId());
            command.setUserId(message.getUserId());
            command.setItems(items);

            orderService.create(command);

        } catch (Exception e) {
            e.printStackTrace();
            throw e; // hoặc log + ACK handling
        }
    }

    private OrderItem toOrderItem(CartItemMessage itemMessage) {
        OrderItem item = new OrderItem(
                null,
                itemMessage.getProductVariantId(),
                itemMessage.getQuantity());
        return item;
    }
}
