package com.letscode.shoppingcartsapi.gateway;

import feign.FeignException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProductsGateway {

    private final ProductsReactiveFeignClient productsReactiveFeignClient;

    public ProductsGateway(ProductsReactiveFeignClient productsReactiveFeignClient) {
        this.productsReactiveFeignClient = productsReactiveFeignClient;
    }

    public Mono<String> getProduct(String productId) {
        return productsReactiveFeignClient.getProduct(productId)
                .onErrorResume(FeignException.NotFound.class, erro ->
                        Mono.empty()
                );
    }
}
