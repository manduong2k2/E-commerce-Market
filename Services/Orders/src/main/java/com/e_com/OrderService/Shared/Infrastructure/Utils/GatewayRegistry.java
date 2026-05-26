package com.e_com.OrderService.Shared.Infrastructure.Utils;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;

@Component
public class GatewayRegistry {
        private final WebClient webClient;
        private final String serviceName;
        private final String serviceUrl;

        public GatewayRegistry(
                        WebClient.Builder builder,
                        @Value("${spring.application.gateway-admin-url}") String baseUrl,
                        @Value("${spring.application.name}") String serviceName,
                        @Value("${spring.application.base-url}") String serviceUrl) {
                this.webClient = builder.baseUrl(baseUrl).build();
                this.serviceName = serviceName;
                this.serviceUrl = serviceUrl;
        }

        @PostConstruct
        public void register() {
                try {
                        // 1. Check service exists
                        webClient.get()
                                        .uri("/services/" + serviceName)
                                        .retrieve()
                                        .bodyToMono(String.class)
                                        .block();

                        webClient.put()
                                        .uri("/services/{name}", serviceName)
                                        .bodyValue(Map.of(
                                                        "name", serviceName,
                                                        "url", serviceUrl))
                                        .retrieve()
                                        .bodyToMono(String.class)
                                        .block();

                } catch (Exception e) {
                        // 2. Create service
                        try {
                                webClient.post()
                                                .uri("/services")
                                                .bodyValue(Map.of(
                                                                "name", serviceName,
                                                                "url", serviceUrl))
                                                .retrieve()
                                                .bodyToMono(String.class)
                                                .block();

                                // 3. Create routes
                                webClient.post()
                                                .uri("/services/" + serviceName + "/routes")
                                                .bodyValue(Map.of(
                                                                "paths", List.of("/" + serviceName),
                                                                "strip_path", true))
                                                .retrieve()
                                                .bodyToMono(String.class)
                                                .block();
                        } catch (Exception ex) {
                                System.out.println("Failed to register service: " + ex.getMessage());
                        }
                }
        }
}
