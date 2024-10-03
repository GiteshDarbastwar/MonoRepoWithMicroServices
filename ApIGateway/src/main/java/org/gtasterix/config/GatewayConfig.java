package org.gtasterix.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product_service", r -> r.path("/products/**")
                        .uri("http://localhost:8081"))  // Product Service
                .route("order_service", r -> r.path("/orders/**")
                        .uri("http://localhost:8082"))  // Order Service
                .route("payment_service", r -> r.path("/payments/**")
                        .uri("http://localhost:8084"))  // Payment Service
                .build();
    }
}
