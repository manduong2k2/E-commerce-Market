package com.e_com.VendorService.Shared.Infrastructure.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class WebClientConfig {
    @Value("${spring.application.gateway-admin-url}")
    private String gatewayAdminUrl;

    @Bean("gatewayWebClient")
    public WebClient gatewayWebClient() {
        return WebClient.builder()
                .baseUrl(gatewayAdminUrl)
                .build();
    }
}
