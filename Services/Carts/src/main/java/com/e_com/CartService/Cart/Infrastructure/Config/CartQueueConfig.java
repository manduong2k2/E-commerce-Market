package com.e_com.CartService.Cart.Infrastructure.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CartQueueConfig {

    public static final String CART_CREATED     = "cart.created";
    public static final String CART_ITEM_ADDED  = "cart.item.added";
    public static final String CART_CHECKED_OUT = "cart.checked-out";

    @Bean
    public Queue cartCreatedQueue() {
        return new Queue(CART_CREATED, true);
    }

    @Bean
    public Queue cartItemAddedQueue() {
        return new Queue(CART_ITEM_ADDED, true);
    }

    @Bean
    public Queue cartCheckedOutQueue() {
        return new Queue(CART_CHECKED_OUT, true);
    }
}
