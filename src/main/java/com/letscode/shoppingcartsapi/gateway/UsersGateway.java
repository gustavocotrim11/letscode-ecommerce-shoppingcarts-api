package com.letscode.shoppingcartsapi.gateway;

import feign.FeignException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UsersGateway {

    private final UsersReactiveFeignClient usersReactiveFeignClient;

    public UsersGateway(UsersReactiveFeignClient usersReactiveFeignClient) {
        this.usersReactiveFeignClient = usersReactiveFeignClient;
    }

    public Mono<String> getUser(String userId) {
        return usersReactiveFeignClient.getUser(userId)
                .onErrorResume(FeignException.NotFound.class, erro ->
                        Mono.empty()
                );
    }
}
