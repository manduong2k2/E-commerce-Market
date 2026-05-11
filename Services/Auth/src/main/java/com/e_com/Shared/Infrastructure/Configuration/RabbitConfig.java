package com.e_com.Shared.Infrastructure.Configuration;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue testQueue() {
        return new Queue("test.queue", true);
    }
}
