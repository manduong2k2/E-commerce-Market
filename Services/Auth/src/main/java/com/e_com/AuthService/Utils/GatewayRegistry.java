package com.e_com.AuthService.Utils;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class GatewayRegistry {
    @Autowired
    @Qualifier("gatewayWebClient")
    public WebClient gatewayWebClient;

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${spring.application.base-url}")
    private String serviceUrl;

    @PostConstruct
    public void register() {
        try {
            // 1. Check service exists
            gatewayWebClient.get()
                    .uri("/services/" + serviceName)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            gatewayWebClient.put()
                    .uri("/services/{name}", serviceName)
                    .bodyValue(Map.of(
                            "name", serviceName,
                            "url", serviceUrl))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            // 2. Create service
            gatewayWebClient.post()
                    .uri("/services")
                    .bodyValue(Map.of(
                            "name", serviceName,
                            "url", serviceUrl))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // 3. Create routes
            gatewayWebClient.post()
                    .uri("/services/" + serviceName + "/routes")
                    .bodyValue(Map.of(
                            "paths", List.of("/"),
                            "strip_path", true))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }
    }
}
