package com.letscode.shoppingcartsapi.gateway;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(name = "products-service")
public interface ProductsReactiveFeignClient {

    @GetMapping("/product/{productId}")
    Mono<String> getProduct(@PathVariable("productId") String productId);
}
