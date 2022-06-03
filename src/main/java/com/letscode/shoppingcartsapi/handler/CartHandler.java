package com.letscode.shoppingcartsapi.handler;

import com.letscode.shoppingcartsapi.domain.Product;
import com.letscode.shoppingcartsapi.service.CartService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
public class CartHandler {

    private final CartService cartService;

    public CartHandler(CartService cartService) {
        this.cartService = cartService;
    }

    public Mono<ServerResponse> getCartById(ServerRequest request) {
        return cartService.getByUserId(request.pathVariable("userId"))
                .flatMap(cart -> ServerResponse.ok().bodyValue(cart))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> addProduct(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(product -> cartService.checkUserAndProduct(request.pathVariable("userId"), product))
                .flatMap(product -> cartService.addProduct(request.pathVariable("userId"), product))
                .flatMap(cart -> ServerResponse.ok().bodyValue(cart))
                .switchIfEmpty(ServerResponse.unprocessableEntity().bodyValue("User id inválido."));
    }

    public Mono<ServerResponse> removeProduct(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(product -> cartService.checkUserAndProduct(request.pathVariable("userId"), product))
                .flatMap(product -> cartService.removeProduct(request.pathVariable("userId"), product))
                .flatMap(cart -> ServerResponse.ok().bodyValue(cart))
                .switchIfEmpty(ServerResponse.unprocessableEntity().bodyValue("User id inválido."));
    }

    public Mono<ServerResponse> changeQuantity(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(product -> cartService.checkUserAndProduct(request.pathVariable("userId"), product))
                .flatMap(product -> cartService.changeQuantity(request.pathVariable("userId"), product))
                .flatMap(cart -> ServerResponse.ok().bodyValue(cart))
                .switchIfEmpty(ServerResponse.unprocessableEntity().bodyValue("User id inválido."));
    }

    public Mono<ServerResponse> finalizePurchase(ServerRequest request) {
        return cartService.finalizePurchase(request.pathVariable("cartId"))
                .flatMap(order -> ServerResponse.ok().bodyValue(order))
                .switchIfEmpty(ServerResponse.unprocessableEntity().bodyValue("User id inválido."));
    }
}
