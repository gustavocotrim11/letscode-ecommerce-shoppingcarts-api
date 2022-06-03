package com.letscode.shoppingcartsapi.config;

import com.letscode.shoppingcartsapi.handler.CartHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.http.MediaType.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> route(CartHandler cartHandler) {
        return RouterFunctions
                .route(GET("/cart/{userId}").and(contentType(APPLICATION_JSON)), cartHandler::getCartById)
                .andRoute(PUT("/cart/{userId}/addProduct").and(contentType(APPLICATION_JSON)), cartHandler::addProduct)
                .andRoute(PUT("/cart/{userId}/removeProduct").and(contentType(APPLICATION_JSON)), cartHandler::removeProduct)
                .andRoute(PUT("/cart/{userId}/changeQuantity").and(contentType(APPLICATION_JSON)), cartHandler::changeQuantity)
                .andRoute(PUT("/cart/{userId}/finalizePurchase").and(contentType(APPLICATION_JSON)), cartHandler::finalizePurchase);
    }
}
