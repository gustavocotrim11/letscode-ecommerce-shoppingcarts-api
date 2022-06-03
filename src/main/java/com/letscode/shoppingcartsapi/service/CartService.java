package com.letscode.shoppingcartsapi.service;

import com.letscode.shoppingcartsapi.domain.Cart;
import com.letscode.shoppingcartsapi.domain.Product;
import com.letscode.shoppingcartsapi.gateway.ProductsGateway;
import com.letscode.shoppingcartsapi.gateway.UsersGateway;
import com.letscode.shoppingcartsapi.repository.CartRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final UsersGateway usersGateway;
    private final ProductsGateway productsGateway;

    public CartService(CartRepository cartRepository, UsersGateway usersGateway, ProductsGateway productsGateway) {
        this.cartRepository = cartRepository;
        this.usersGateway = usersGateway;
        this.productsGateway = productsGateway;
    }

    private Cart createCartUtil(String userId, Product product) {
        Cart newCart = new Cart();
        newCart.setStatus("open");
        newCart.setUserId(userId);
        newCart.addProduct(product);
        return newCart;
    }

    public Mono<Cart> getByUserId(String userId) {
        return cartRepository.findByUserIdAndStatus(userId, "open");
    }

    public Mono<Cart> addProduct(String userId, Product product) {
        return cartRepository.findByUserIdAndStatus(userId, "open")
                .map(cart -> {
                    cart.addProduct(product);
                    return cart;
                })
                .flatMap(cartRepository::save)
                .switchIfEmpty(cartRepository.save(createCartUtil(userId, product)));
    }

    public Mono<Cart> removeProduct(String userId, Product product) {
        return cartRepository.findByUserIdAndStatus(userId, "open").map(cart -> {
            cart.removeProduct(product);
            return cart;
        }).flatMap(cartRepository::save);
    }

    public Mono<Cart> changeQuantity(String userId, Product product) {
        return cartRepository.findByUserIdAndStatus(userId, "open").map(cart -> {
            cart.removeProduct(product);
            cart.addProduct(product);
            return cart;
        }).flatMap(cartRepository::save);
    }

    public Mono<String> finalizePurchase(String userId) {
        cartRepository.findByUserIdAndStatus(userId, "open").map(cart -> {
            cart.setStatus("closed");
            return cart;
        }).flatMap(cartRepository::save);

        return Mono.just("order generated successfully");
    }

    public Mono<Product> checkUserAndProduct(String userId, Product product) {
        return Mono.zip(
                Mono.just(userId).flatMap(u -> usersGateway.getUser(userId)),
                Mono.just(product).flatMap(p -> productsGateway.getProduct(p.getId()))
        ).map(t -> product);
    }
}
