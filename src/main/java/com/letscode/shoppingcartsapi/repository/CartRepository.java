package com.letscode.shoppingcartsapi.repository;

import com.letscode.shoppingcartsapi.domain.Cart;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CartRepository extends ReactiveMongoRepository<Cart, String> {
    Mono<Cart> findByUserIdAndStatus(String userId, String status);
}
